package com.galuszkat.microservice.security.service;

import com.galuszkat.microservice.security.domain.LoginCredentials;
import com.galuszkat.microservice.security.domain.LoginToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

  private static final String DEFAULT_USERNAME = "guest";
  private static final String DEFAULT_PASSWORD = "guest";
  private final JWTGenerateService service;

  public Mono<LoginToken> login(LoginCredentials credentials) {
    if (DEFAULT_USERNAME.equals(credentials.getUsername()) && DEFAULT_PASSWORD.equals(credentials.getPassword())) {
      Instant oneHour = Instant.now().plus(Duration.ofHours(1));
      return Mono.fromCallable(() -> service.generate(DEFAULT_USERNAME, List.of("ROLE_ADMIN"), oneHour))
          .map(token -> new LoginToken(token, oneHour));
    } else {
      throw new AccessDeniedException("Unauthorized");
    }
  }
}