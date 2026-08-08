package com.gerardo.thrive.auth.services;

import com.gerardo.thrive.auth.dtos.request.RefreshTokenCreateDto;
import com.gerardo.thrive.auth.mappers.RefreshTokenMapper;
import com.gerardo.thrive.auth.repositories.RefreshTokenRepository;
import com.gerardo.thrive.user.entities.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.token.SecureRandomFactoryBean;
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
    @Value("$JWT_RT_DURATION")
    private Long duration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenMapper refreshTokenMapper;

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
