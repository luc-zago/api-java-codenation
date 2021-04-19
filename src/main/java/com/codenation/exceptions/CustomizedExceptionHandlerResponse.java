package com.codenation.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomizedExceptionHandlerResponse {

    private int StatusCode;
    private String message;
    private LocalDateTime timestamp;
}
