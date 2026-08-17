package com.gerardo.thrive.common.dtos;

import org.springframework.data.web.PagedModel.PageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;


public record ApiResponse<T>(
        String uri, String message, int statusCode, LocalDateTime timestamp, T data, PageMetadata metadata
) {
    public static <T> ApiResponse<T> ok(T data, String message) {
        return build(data, message, HttpStatus.OK, null);
    }

    public static <T> ApiResponse<T> created(T data, String message) {
        return build(data, message, HttpStatus.CREATED, null);
    }

    public static <T> ApiResponse<T> updated(T data, String message) {
        return build(data, message, HttpStatus.OK, null);
    }

    public static <T> ApiResponse<T> deleted(T data, String message) {
        return build(data, message, HttpStatus.OK, null);
    }


    public static <T> ApiResponse<T> paged(T data, String message, PageMetadata pageMetadata) {
        return build(data, message, HttpStatus.OK, pageMetadata);
    }

    private static <T> ApiResponse<T> build(T data, String message, HttpStatus httpStatus, PageMetadata pageMetadata) {
        String uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .build()
                .toUriString();
        return new ApiResponse<>(uri, message, httpStatus.value(), LocalDateTime.now(), data, pageMetadata);
    }
}
