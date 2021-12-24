package com.galuszkat.microservice.security.service.configuration;

import com.galuszkat.microservice.security.service.JWTValidateService;
import com.galuszkat.microservice.security.service.convert.JWTAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.server.WebFilter;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class JWTSecurityConfiguration {

  private final JWTValidateService validateJwtService;
  private final JWTAuthenticationConverter converter;

  @Bean
  public SecurityWebFilterChain blogSecurityFilterConfiguration(ServerHttpSecurity httpSecurityBuilder) {
    return httpSecurityBuilder
        .formLogin().disable() // disable basic auth with html
        .logout().disable() // disable form logout page
        .csrf().disable() // disable csrf (only rest calls not used in browser)
        .httpBasic().disable() ///z disable basic auth
        .authorizeExchange(customizer -> {
            customizer
                .pathMatchers("/admin/**").hasRole("ADMIN") // /admin has to be authenticated
                .anyExchange().permitAll(); // other request can be accessed without authentication
          })
        .addFilterAfter(jwtAuthenticationWebFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
        .build();
  }

  @Bean // disable creation of basic auth authenticationManager
  public ReactiveUserDetailsService userDetailsService() {
    return null;
  }

  private WebFilter jwtAuthenticationWebFilter() {
    AuthenticationWebFilter filter = new AuthenticationWebFilter(jwtAuthenticationManager());

    // set conversion from header to Authentication
    filter.setServerAuthenticationConverter(converter);
    // set response status code to unauthorized in case of authentication failure
    filter.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(new HttpStatusServerEntryPoint(UNAUTHORIZED)));
    // set disable storing security context in session
    filter.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance());

    return filter;
  }

  private ReactiveAuthenticationManager jwtAuthenticationManager() {
    return new ReactiveAuthenticationManagerAdapter(new JWTAuthenticationManager(validateJwtService));
  }

}