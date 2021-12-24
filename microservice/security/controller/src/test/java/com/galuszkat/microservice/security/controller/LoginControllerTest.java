package com.galuszkat.microservice.security.controller;

import com.galuszkat.microservice.security.domain.LoginCredentials;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest
@AutoConfigureWebTestClient
public class LoginControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void whenValidCredentials_ShouldReturnToken() {
    LoginCredentials payload = new LoginCredentials();
    payload.setUsername("guest");
    payload.setPassword("guest");

    webTestClient.post()
        .uri("/login")
        .body(BodyInserters.fromValue(payload))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.token").value(Matchers.notNullValue())
        .jsonPath("$.validUntil").value(Matchers.notNullValue());
  }

}