package com.gerardo.thrive.auth.services;

import com.gerardo.thrive.auth.dtos.request.RefreshTokenCreateDto;
import com.gerardo.thrive.auth.dtos.response.RefreshAccessTokenResponseDto;
import com.gerardo.thrive.auth.entities.RefreshTokenModel;
import com.gerardo.thrive.auth.exceptions.TokenAlreadyUsedException;
import com.gerardo.thrive.auth.exceptions.TokenExpiredException;
import com.gerardo.thrive.auth.mappers.RefreshTokenMapper;
import com.gerardo.thrive.auth.repositories.RefreshTokenRepository;
import com.gerardo.thrive.user.entities.UserModel;
import com.gerardo.thrive.user.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HexFormat;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${jwt.refresh-expiration}")
    private long duration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenMapper refreshTokenMapper;
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    public String issueRefreshToken(UserModel user) {
        UUID familyId = UUID.randomUUID();
        String refreshToken = generateRawToken();
        Instant expires = Instant.now()
                .plus(duration, ChronoUnit.DAYS);

        refreshTokenRepository.save(refreshTokenMapper.toModel(RefreshTokenCreateDto.builder()
                .familyId(familyId)
                .tokenHash(hashToken(refreshToken))
                .user(user)
                .expiresAt(expires)
                .build()));

        return refreshToken;
    }

    public void logout(String rawRefreshToken) throws NoSuchAlgorithmException {
        String incomingRawRefreshTokenHash = hashToken(rawRefreshToken);
        RefreshTokenModel storeRefreshTokenHash = refreshTokenRepository.findByTokenHash(incomingRawRefreshTokenHash)
                .orElseThrow(() -> new EntityNotFoundException(
                        "logout failed: the provided refresh token does not match any active session"));
        revokeFamily(storeRefreshTokenHash.getFamilyId());
    }

    public RefreshAccessTokenResponseDto refreshAccessToken(String rawToken) {
        String incomingRawRefreshTokenHash = hashToken(rawToken);
        RefreshTokenModel storedRefreshTokenHash = refreshTokenRepository.findByTokenHash(incomingRawRefreshTokenHash)
                .orElseThrow(() -> new EntityNotFoundException(
                        "token refresh failed: the provided refresh token does not match any active session"));
        if (storedRefreshTokenHash.getExpiresAt()
                .isBefore(Instant.now())) {
            storedRefreshTokenHash.setRevokedAt(Instant.now());
            refreshTokenRepository.save(storedRefreshTokenHash);
            throw new TokenExpiredException("refresh token", "expiresAt",
                    storedRefreshTokenHash.getExpiresAt());
        }
        String newStringRefreshToken = generateRawToken();
        RefreshTokenModel newRefreshTokenModel = RefreshTokenModel.builder()
                .tokenHash(hashToken(newStringRefreshToken))
                .userId(storedRefreshTokenHash.getUserId())
                .createdAt(Instant.now())
                .expiresAt(Instant.now()
                        .plus(duration, ChronoUnit.DAYS))
                .build();

        boolean isIncomingRefreshTokenUsable = storedRefreshTokenHash.getRevokedAt() != null; //Token was already used

        if (isIncomingRefreshTokenUsable) {
            //This means revoke is populated. Therefore, the refresh token was previously used to generate a new access token
            //I have to invalid the family linage
            revokeFamily(storedRefreshTokenHash.getFamilyId());
            throw new TokenAlreadyUsedException("refresh token family", "familyId",
                    storedRefreshTokenHash.getFamilyId());
        } else {
            storedRefreshTokenHash.setRevokedAt(Instant.now());
            newRefreshTokenModel.setFamilyId(storedRefreshTokenHash.getFamilyId());
            refreshTokenRepository.save(newRefreshTokenModel);
        }

        UserModel user = storedRefreshTokenHash.getUserId();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());

        return RefreshAccessTokenResponseDto.builder()
                .refreshToken(newStringRefreshToken)
                .accessToken(jwtService.generateToken(userDetails))
                .build();
    }

    private void revokeFamily(UUID family_id) {
        refreshTokenRepository.findAllByFamilyId(family_id)
                .forEach(rt -> {
                    rt.setRevokedAt(Instant.now());
                    refreshTokenRepository.save(rt);
                });
    }

    private String generateRawToken() {
        {
            SecureRandom secureRandom = new SecureRandom();
            //Empty bytes array -> capacity  is 32
            byte[] bytes = new byte[32];
            //Secure random adds in each position random values
            secureRandom.nextBytes(bytes);
            //Encode the raw token (make it more readable) and return as string (take bytes as parameter)
            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(bytes);

        }
    }

    private String hashToken(String rawToken) {
        try {
            //I'll use this class to use some methods and hash the token, I set the algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //I stored the hashed bytes in an array
            byte[] hash = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
            //Return hash as string format
            return HexFormat.of()
                    .formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            //SHA-256 is required on every JVM, so this can never happen at runtime
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }
}
