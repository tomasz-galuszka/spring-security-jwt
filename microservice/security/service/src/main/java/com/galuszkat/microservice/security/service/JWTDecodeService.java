package com.galuszkat.microservice.security.service;

import com.galuszkat.microservice.security.domain.exception.JWTDecodingException;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class JWTDecodeService {

  public JWT decode(String token) {
    try {
      if (token.isBlank()) {
        throw new JWTDecodingException("Blank token provided");
      }
      JWT decoded = JWTParser.parse(token);
      log.info("Token has been successfully decoded to jwt object");
      return decoded;
    } catch (ParseException e) {
      throw new JWTDecodingException(e);
    }
  }
}