package com.dev.userserver.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ErrorMessage {
  private int statusCode;
  private final LocalDateTime timestamp = LocalDateTime.now();
  private String message;
  private String description;
}
