package com.gerardo.thrive.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

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

    private String hashToken(String rawToken) throws NoSuchAlgorithmException {
        //I'll use this class to use some methods and hash the token, I set the algorithm
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        //I stored the hashed bytes in an array
        byte[] hash = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
        //Return hash as string format
        return HexFormat.of()
                .formatHex(hash);
    }
}
