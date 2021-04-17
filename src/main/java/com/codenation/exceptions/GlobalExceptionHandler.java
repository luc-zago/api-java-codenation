package com.codenation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomizedExceptionHandlerResponse> handleException (MethodArgumentNotValidException exception) {
        LocalDateTime time = LocalDateTime.now();
        int code = HttpStatus.BAD_REQUEST.value();
        String message = exception.getFieldError().getDefaultMessage();
        CustomizedExceptionHandlerResponse error = new CustomizedExceptionHandlerResponse(code, message, time);
        return new ResponseEntity<CustomizedExceptionHandlerResponse>(error, HttpStatus.BAD_REQUEST);
    }
}
