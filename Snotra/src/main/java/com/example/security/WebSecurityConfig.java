package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .formLogin().disable()
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/orders/public/**").permitAll()
                        .requestMatchers("/orders/admin/**").hasRole("ADMIN")
                        .requestMatchers("/orders/form").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
