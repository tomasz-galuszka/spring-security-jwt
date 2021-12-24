package com.galuszkat.microservice.security.api;

import com.galuszkat.microservice.security.domain.LoginCredentials;
import com.galuszkat.microservice.security.domain.LoginToken;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@RequestMapping("/login")
public interface LoginAPI {

  @PostMapping
  Mono<LoginToken> login(@RequestBody @Valid LoginCredentials credentials);

}