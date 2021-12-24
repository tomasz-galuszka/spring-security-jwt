package com.galuszkat.microservice.security.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginCredentials {

  @NotBlank
  private String username;
  @NotBlank
  private String password;
}