package com.galuszkat.microservice.security.controller;

import com.galuszkat.microservice.security.api.LoginAPI;
import com.galuszkat.microservice.security.domain.LoginCredentials;
import com.galuszkat.microservice.security.domain.LoginToken;
import com.galuszkat.microservice.security.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class LoginController implements LoginAPI {

  private final LoginService service;

  @Override
  public Mono<LoginToken> login(LoginCredentials credentials) {
    return service.login(credentials);
  }
}