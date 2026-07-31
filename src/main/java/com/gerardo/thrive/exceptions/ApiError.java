package com.gerardo.thrive.exceptions;

import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.Instant;


public record ApiError(
        URI uri,
        String message,
        String errorCode,
        Instant timestamp,
        HttpStatus httpStatus

) {
}
