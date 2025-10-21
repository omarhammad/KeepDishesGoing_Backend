package com.omarhammad.kdg_backend.orders.adapters.out.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class OrdersSecurityConfig {

    @Bean
    public SecurityFilterChain orderFilterChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/api/orders/**")
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/orders/**").permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(mgmt ->
                        mgmt.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }
}
