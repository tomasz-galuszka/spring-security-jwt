package com.galuszkat.microservice.hello.api;

import com.galuszkat.microservice.security.domain.LoggedUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@RequestMapping("/admin")
public interface HelloAPI {

  @GetMapping("/hello")
  Mono<String> hello(@AuthenticationPrincipal LoggedUser loggedUser);
}