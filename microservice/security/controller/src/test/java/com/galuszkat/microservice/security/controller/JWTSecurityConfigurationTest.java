package com.galuszkat.microservice.security.controller;

import com.galuszkat.microservice.security.service.JWTGenerateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest
@AutoConfigureWebTestClient
class JWTSecurityConfigurationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private JWTGenerateService generateJwtService;

  @Test
  void whenValidJwtToken_ShouldReturnOK() {
    String jwt = generateJwtService.generate("test-account", List.of("ROLE_ADMIN"), Instant.now().plus(Duration.ofHours(1)));

    webTestClient.get()
        .uri("/admin/test")
        .header(AUTHORIZATION, "Bearer " + jwt)
        .exchange()
        .expectStatus().isOk();
  }

  @Test
  void whenInValidJwtToken_ShouldReturnUnauthorized() {
    String jwt = generateJwtService.generate("test-account", List.of("ROLE_ADMIN"), Instant.now().plus(Duration.ofHours(1)));

    webTestClient.get()
        .uri("/admin/test")
        .header(AUTHORIZATION, "Bearer " + jwt + "xxx")
        .exchange()
        .expectStatus().isUnauthorized();
  }

  @Test
  void whenTokenExpired_ShouldReturnUnauthorized() {
    String jwt = generateJwtService.generate("test-account", List.of("ROLE_ADMIN"), Instant.now().minus(Duration.ofHours(1)));

    webTestClient.get()
        .uri("/admin/test")
        .header(AUTHORIZATION, "Bearer " + jwt)
        .exchange()
        .expectStatus().isUnauthorized();
  }

  @Test
  void whenInvalidRole_ShouldReturnForbidden() {
    String jwt = generateJwtService.generate("tomeczek", List.of("ROLE_GUEST"), Instant.now().plus(Duration.ofHours(1)));

    webTestClient.get()
        .uri("/admin/test")
        .header(AUTHORIZATION, "Bearer " + jwt)
        .exchange()
        .expectStatus().isForbidden();
  }

  @Test
  void whenPathDoesNotStartWithAdminAndNoTokenProvided_ShouldReturnOk() {
    webTestClient.get()
        .uri("/public/hello")
        .exchange()
        .expectStatus().isOk();
  }

}