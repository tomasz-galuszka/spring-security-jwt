package com.galuszkat.microservice.hello.controller;

import com.galuszkat.microservice.hello.api.HelloAPI;
import com.galuszkat.microservice.security.domain.LoggedUser;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController implements HelloAPI {

  @Override
  public Mono<String> hello(LoggedUser loggedUser) {
    return Mono.just("Protected hello accessed by: " + loggedUser.getName());
  }
}