package com.gerardo.thrive.auth.exceptions;

public class TokenAlreadyUsedException extends ApiException {
    public TokenAlreadyUsedException(String resource, String field, Object value) {
        super(resource, field,
                "the token you are trying to use is revoked, this might be catalogue as malicious temper", value);
    }
}
