package com.gerardo.thrive.common.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GeneralResponseDto {
    private String uri;
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private Object data;
}
