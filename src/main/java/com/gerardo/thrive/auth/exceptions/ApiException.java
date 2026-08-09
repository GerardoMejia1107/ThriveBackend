package com.gerardo.thrive.auth.exceptions;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final String resource;
    private final String field;
    private final Object value;
    private final String rule;

    ApiException(String _resource, String _field, String _rule, Object _value) {
        super(String.format(
                "%s violation in %s with value %s. Rule is: %s",
                _resource, _field, _value, _rule
        ));
        this.resource = _resource;
        this.field = _field;
        this.value = _value;
        this.rule = _rule;
    }
}
