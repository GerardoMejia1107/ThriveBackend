package com.gerardo.thrive.config.security;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {
    @Override
    public @Nullable String encode(@Nullable CharSequence charSequence) {
        if (charSequence == null) return null;
        return BCrypt.hashpw(charSequence.toString(), BCrypt.gensalt(4));
    }

    @Override
    public boolean matches(@Nullable CharSequence charSequence, @Nullable String s) {
        if (charSequence == null || s == null) return false;
        return BCrypt.checkpw(charSequence.toString(), s);
    }
}
