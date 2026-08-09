package com.gerardo.thrive.auth.exceptions;

public class TokenExpiredException extends ApiException {
    public TokenExpiredException(String resource, String field, Object value) {
        super(resource, field, "token expiration date must be valid", value);
    }
}
