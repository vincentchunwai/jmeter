package com.dev.userserver.exception;

import lombok.Getter;

@Getter
public enum Code {
  EMAIL_ALREADY_EXISTS,
  USER_NOT_SAVED,
  INTERNAL_SERVER_ERROR,
  ;
}
