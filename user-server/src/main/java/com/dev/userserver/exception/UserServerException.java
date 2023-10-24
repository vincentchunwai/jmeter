package com.dev.userserver.exception;

public class UserServerException extends RuntimeException{
  
  public UserServerException(Code code){
    super(code.name());
  }
}
