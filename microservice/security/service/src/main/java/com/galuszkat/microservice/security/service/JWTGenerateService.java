package com.galuszkat.microservice.security.service;

import com.galuszkat.microservice.security.domain.exception.JWTGenerationException;
import com.galuszkat.microservice.security.service.property.JWTSecurityProperties;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JWTGenerateService {

  private final JWTSecurityProperties properties;

  public String generate(String userName, List<String> roles, Instant expirationUTC) {
    try {
      JWSSigner signer = new RSASSASigner(properties.getPrivateKey());

      JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
          .subject(userName)
          .issuer("blog-service")
          .claim("authorities", roles)
          .expirationTime(Date.from(expirationUTC))
          .build();

      SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).build(), claimsSet);
      signedJWT.sign(signer);

      log.info("Token has been generated for user: {}", userName);
      return signedJWT.serialize();

    } catch (JOSEException e) {
      throw new JWTGenerationException(e);
    }
  }
}