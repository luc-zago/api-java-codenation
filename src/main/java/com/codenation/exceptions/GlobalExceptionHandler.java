package com.codenation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<CustomizedExceptionHandlerResponse> exceptionResponse(String message) {
        LocalDateTime time = LocalDateTime.now();
        int code = HttpStatus.BAD_REQUEST.value();
        CustomizedExceptionHandlerResponse error = new CustomizedExceptionHandlerResponse(
                code, message, time);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomizedExceptionHandlerResponse> handleArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        String message = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        return exceptionResponse(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomizedExceptionHandlerResponse> exceptionHandler(
            Exception exception) {
        String message = exception.getMessage();
        return exceptionResponse(message);
    }
}
