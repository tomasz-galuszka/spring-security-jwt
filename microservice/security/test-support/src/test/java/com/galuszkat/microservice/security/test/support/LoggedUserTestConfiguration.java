package com.galuszkat.microservice.security.test.support;

import com.galuszkat.microservice.security.domain.LoggedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class LoggedUserTestConfiguration implements WebFluxConfigurer {

  public static final String LOGGED_USERNAME = "test-user";

  @Override
  public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
    configurer.addCustomResolver(new HandlerMethodArgumentResolver() {
      @Override
      public boolean supportsParameter(MethodParameter parameter) {
        return LoggedUser.class.isAssignableFrom(parameter.getParameterType());
      }
      @Override
      public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        LoggedUser loggedUser = new LoggedUser(LOGGED_USERNAME);
        log.info("Logged user: {}", loggedUser);

        return Mono.just(loggedUser);
      }
    });
  }
}