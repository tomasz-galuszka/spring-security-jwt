package com.galuszkat.microservice.security.service.configuration;

import com.galuszkat.microservice.security.service.JWTValidateService;
import com.nimbusds.jwt.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

@RequiredArgsConstructor
public class JWTAuthenticationManager implements AuthenticationManager {

  private final JWTValidateService validateJwtService;

  public Authentication authenticate(Authentication authentication) {
    authentication.setAuthenticated(hasValidSignature(authentication) && isNotExpired(authentication));
    if (!authentication.isAuthenticated()) {
      throw new BadCredentialsException("invalid credentials");
    }
    return authentication;
  }

  private boolean hasValidSignature(Authentication authentication) {
    return validateJwtService.isValid(asJWT(authentication));
  }

  private boolean isNotExpired(Authentication authentication) {
    return validateJwtService.isNotExpired(asJWT(authentication));
  }

  private JWT asJWT(Authentication authentication) {
    return (JWT) authentication.getCredentials();
  }

}