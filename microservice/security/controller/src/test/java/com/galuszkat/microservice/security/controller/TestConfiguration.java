package com.galuszkat.microservice.security.controller;

import com.galuszkat.microservice.security.domain.LoggedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@SpringBootApplication(scanBasePackages = "com.galuszkat")
public class TestConfiguration {

  @RestController
  @RequestMapping("/admin/test")
  public static class AdminController {

    @GetMapping
    public Mono<String> getHello(@AuthenticationPrincipal LoggedUser loggedUser) {
      log.info("User: {}", loggedUser);
      return Mono.just("Hello");
    }

  }

  @RestController
  @RequestMapping("/public/hello")
  public static class PublicController {

    @GetMapping
    public Mono<String> getHello() {
      return Mono.just("hello public");
    }

  }
}