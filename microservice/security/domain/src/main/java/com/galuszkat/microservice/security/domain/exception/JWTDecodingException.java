package com.galuszkat.microservice.security.domain.exception;

public class JWTDecodingException extends RuntimeException {

  public JWTDecodingException(Throwable cause) {
    super("Unauthorized", cause);
  }

  public JWTDecodingException(String msg) {
    super(msg);
  }
}