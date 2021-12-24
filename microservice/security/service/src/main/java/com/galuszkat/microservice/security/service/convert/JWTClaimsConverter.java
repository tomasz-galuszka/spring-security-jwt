package com.galuszkat.microservice.security.service.convert;

import com.galuszkat.microservice.security.domain.LoggedUser;
import com.galuszkat.microservice.security.domain.exception.JWTClaimsException;
import com.nimbusds.jwt.JWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JWTClaimsConverter {

  public LoggedUser loggedUser(JWT signedJWT) {
    try {
      return new LoggedUser(
          signedJWT.getJWTClaimsSet().getSubject()
      );
    } catch (ParseException e) {
      throw new JWTClaimsException(e);
    }
  }

  public Set<GrantedAuthority> authorities(JWT signedJWT) {
    try {
      return Arrays.stream(signedJWT.getJWTClaimsSet().getStringArrayClaim("authorities"))
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toSet());
    } catch (ParseException e) {
      throw new JWTClaimsException(e);
    }
  }
}