package com.galuszkat.microservice.security.service.property;

import com.galuszkat.microservice.security.domain.exception.JWTKeyParsingException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.nimbusds.jose.jwk.JWK.parseFromPEMEncodedObjects;

@Slf4j
@Getter
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties("security")
public class JWTSecurityProperties {

  private RSAKey privateKey;
  private RSAKey publicKey;

  public void setPrivateKey(String privateKey) {
    try {
      log.info("setPrivateKey: {}", privateKey.length());
      this.privateKey = parseFromPEMEncodedObjects(privateKey).toRSAKey();
    } catch (JOSEException e) {
      throw new JWTKeyParsingException(e);
    }
  }

  public void setPublicKey(String publicKey) {
    try {
      log.info("setPublicKey: {}", publicKey.length());
      this.publicKey = parseFromPEMEncodedObjects(publicKey).toRSAKey();
    } catch (JOSEException e) {
      throw new JWTKeyParsingException(e);
    }
  }
}