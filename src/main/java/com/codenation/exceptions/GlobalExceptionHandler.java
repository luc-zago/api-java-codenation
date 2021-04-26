package com.codenation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<CustomizedExceptionHandlerResponse> exceptionResponse(int code, String message) {
        LocalDateTime time = LocalDateTime.now();
        CustomizedExceptionHandlerResponse error = new CustomizedExceptionHandlerResponse(
                code, message, time);
        return new ResponseEntity<CustomizedExceptionHandlerResponse>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomizedExceptionHandlerResponse> handleArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        String message = exception.getFieldError().getDefaultMessage();
        int code = HttpStatus.BAD_REQUEST.value();
        return exceptionResponse(code, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomizedExceptionHandlerResponse> exceptionHandler(
            Exception exception) {
        String message = exception.getMessage();
        int code = HttpStatus.CONFLICT.value();
        if (exception.getClass().getName().contains("NoSuchElement")) {
            code = HttpStatus.NOT_FOUND.value();
        }
        return exceptionResponse(code, message);
    }
}
