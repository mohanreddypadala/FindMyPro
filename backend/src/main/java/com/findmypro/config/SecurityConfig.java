package com.findmypro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll() // Allow all requests
                .anyRequest().authenticated()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")                  // default logout URL
                .logoutSuccessUrl("/select")           // redirect here after logout
                .invalidateHttpSession(true)           // clear session
                .clearAuthentication(true)
                .permitAll()
            );

        return http.build();
    }
}
