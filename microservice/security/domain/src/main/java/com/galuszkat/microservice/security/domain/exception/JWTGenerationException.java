package com.galuszkat.microservice.security.domain.exception;

public class JWTGenerationException extends RuntimeException {

  public JWTGenerationException(Throwable cause) {
    super("Token generation failed", cause);
  }
}