package com.galuszkat.microservice.security.domain.exception;

public class JWTValidationException extends RuntimeException {

  public JWTValidationException(Throwable cause) {
    super("Unauthorized", cause);
  }
}