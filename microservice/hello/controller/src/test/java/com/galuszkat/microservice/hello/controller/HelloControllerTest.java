package com.galuszkat.microservice.hello.controller;

import static com.galuszkat.microservice.security.test.support.LoggedUserTestConfiguration.LOGGED_USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class HelloControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void whenMakeGetRequest_ShouldReturnHelloMessage () {
    webTestClient.get()
        .uri("/admin/hello")
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class).value(equalTo("Protected hello accessed by: " + LOGGED_USERNAME));
  }

}