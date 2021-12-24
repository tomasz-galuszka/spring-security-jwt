package com.galuszkat.microservice.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class LoginToken {

  private String token;
  private Instant validUntil;
}