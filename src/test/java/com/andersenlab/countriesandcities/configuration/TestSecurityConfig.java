//package com.andersenlab.countriesandcities.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@Profile("test")
//public class TestSecurityConfig {
//
//  @Configuration
//  public static class TestSecurityConfigurerAdapter extends AbstractHttpConfigurer<TestSecurityConfigurerAdapter, HttpSecurity> {
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//      http
//          .csrf(AbstractHttpConfigurer::disable)
//          .authorizeHttpRequests(authorize -> authorize
//              .requestMatchers(HttpMethod.GET, "/**").permitAll()
//              .requestMatchers(HttpMethod.PATCH, "/cities/{id}").permitAll()
//              .anyRequest().permitAll()
//          );
//    }
//  }
//
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http, TestSecurityConfigurerAdapter configurerAdapter) throws Exception {
//    http.apply(configurerAdapter);
//    return http.build();
//  }
//}
