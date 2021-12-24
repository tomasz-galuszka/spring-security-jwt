package com.galuszkat.microservice.security.domain.exception;

public class JWTClaimsException extends RuntimeException {

  public JWTClaimsException(Throwable cause) {
    super("Token claims parsing exception", cause);
  }
}