package com.dev.userserver.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class ControllerExceptionHandler {
  
  @ExceptionHandler(UserServerException.class)
  @ResponseStatus(value = HttpStatus.CONFLICT)
  public ErrorMessage emailAlreadyExistsException(UserServerException ex, WebRequest webRequest){
    ErrorMessage message = ErrorMessage.builder()
        .statusCode(HttpStatus.CONFLICT.value())
        .description(webRequest.getDescription(false))
        .message(ex.getMessage())
        .build();
    return message;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage globalExceptionHandler(Exception ex, WebRequest webRequest){
    ErrorMessage message = ErrorMessage.builder()
      .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
      .description(webRequest.getDescription(false))
      .message(ex.getMessage())
      .build();
    return message;
  }
}
