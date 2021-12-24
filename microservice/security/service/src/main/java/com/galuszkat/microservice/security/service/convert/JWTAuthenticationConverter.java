package com.galuszkat.microservice.security.service.convert;

import com.galuszkat.microservice.security.service.JWTDecodeService;
import com.galuszkat.microservice.security.service.configuration.JwtAuthenticationToken;
import com.nimbusds.jwt.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationConverter implements ServerAuthenticationConverter {

  private static final String BEARER = "Bearer ";
  private final JWTDecodeService decodeJwtService;
  private final JWTClaimsConverter claimsConverter;

  @Override
  public Mono<Authentication> convert(ServerWebExchange exchange) {
    String authorization = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);

    if (!StringUtils.hasLength(authorization)) {
      log.info("No {} header provided", AUTHORIZATION);
      return Mono.empty();
    }
    if (!StringUtils.startsWithIgnoreCase(authorization, BEARER)) {
      log.warn("{} header does not start with: {}", AUTHORIZATION, BEARER);
      return Mono.empty();
    }

    String token = (authorization.length() <= BEARER.length()) ? "" : authorization.substring(BEARER.length());

    JWT jwt = decodeJwtService.decode(token);
    return Mono.just(new JwtAuthenticationToken(jwt, claimsConverter.loggedUser(jwt), claimsConverter.authorities(jwt)));
  }

}