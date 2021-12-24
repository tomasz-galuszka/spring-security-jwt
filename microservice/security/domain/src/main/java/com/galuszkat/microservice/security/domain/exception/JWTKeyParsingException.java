package com.galuszkat.microservice.security.domain.exception;

public class JWTKeyParsingException extends RuntimeException {

  public JWTKeyParsingException(Throwable e) {
    super("Unable to parse PEM key from configuration", e);
  }
}