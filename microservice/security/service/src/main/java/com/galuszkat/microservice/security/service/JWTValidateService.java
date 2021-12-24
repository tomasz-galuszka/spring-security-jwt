package com.galuszkat.microservice.security.service;

import com.galuszkat.microservice.security.domain.exception.JWTValidationException;
import com.galuszkat.microservice.security.service.property.JWTSecurityProperties;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class JWTValidateService {

  private final JWTSecurityProperties properties;

  public boolean isValid(JWT jwt) {
    try {
      JWSVerifier verifier = new RSASSAVerifier(properties.getPublicKey());
      SignedJWT signedJWT = new SignedJWT(jwt.getParsedParts()[0], jwt.getParsedParts()[1], jwt.getParsedParts()[2]);
      boolean result = signedJWT.verify(verifier);

      log.info("Token has valid signature: {}", result);
      return result;
    } catch (ParseException | JOSEException e) {
      throw new JWTValidationException(e);
    }
  }

  public boolean isNotExpired(JWT jwt) {
    try {
      boolean result = jwt.getJWTClaimsSet().getExpirationTime().toInstant().isAfter(Instant.now());

      log.info("Token is up to date: {}", result);
      return result;
    } catch (ParseException e) {
      throw new BadCredentialsException("invalid expiration claim");
    }
  }

}