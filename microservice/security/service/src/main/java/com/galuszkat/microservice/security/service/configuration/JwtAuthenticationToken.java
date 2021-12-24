package com.galuszkat.microservice.security.service.configuration;

import com.galuszkat.microservice.security.domain.LoggedUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

  private final Object jwtData;
  private final LoggedUser loggedUser;

  public JwtAuthenticationToken(Object jwtData, LoggedUser loggedUser, Set<GrantedAuthority> authoritySet) {
    super(authoritySet);
    this.jwtData = jwtData;
    this.loggedUser = loggedUser;
  }

  @Override
  public Object getCredentials() {
    return jwtData;
  }

  @Override
  public Object getPrincipal() {
    return this.loggedUser;
  }
}