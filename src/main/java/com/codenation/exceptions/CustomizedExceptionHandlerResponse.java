package com.codenation.exceptions;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomizedExceptionHandlerResponse {

    private int StatusCode;
    private String message;
    private LocalDateTime timestamp;
}
