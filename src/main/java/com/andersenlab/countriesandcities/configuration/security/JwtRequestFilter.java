package com.andersenlab.countriesandcities.configuration.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

  private static final String BEARER = "Bearer";
  private static final Base64.Decoder decoder = Base64.getDecoder();
  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    var authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authorizationHeader == null) {
      chain.doFilter(request, response);
    } else {
      var jwt = authorizationHeader.replace(BEARER, "");
      var chunks = jwt.split("\\.");
      var payloadString = new String(decoder.decode(chunks[1]));
      var payload = objectMapper.readValue(payloadString, JwtPayload.class);
      String[] roles = payload.getRoles();

      List<GrantedAuthority> authorities = Arrays.stream(roles)
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList());
      Authentication authentication = new UsernamePasswordAuthenticationToken(
          payload.getUsername(), null, authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      chain.doFilter(request, response);
    }
  }

  @Data
  private static class JwtPayload {

    @JsonProperty("sub")
    private Long subject;
    private String[] roles;
    @JsonProperty("iat")
    private Long issuedAt;
    @JsonProperty("name")
    private String username;
  }
}
