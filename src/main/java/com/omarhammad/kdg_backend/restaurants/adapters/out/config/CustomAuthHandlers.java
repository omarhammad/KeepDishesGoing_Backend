package com.omarhammad.kdg_backend.restaurants.adapters.out.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.omarhammad.kdg_backend.restaurants.adapters.in.dto.generic.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthHandlers {
    private final ObjectMapper objectMapper;


    public CustomAuthHandlers() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    public AuthenticationEntryPoint authenticationEntryPoint() {

        return (request, response, authException) -> {
            ErrorResponseDTO error = new ErrorResponseDTO(
                    request.getRequestURI(),
                    HttpStatus.UNAUTHORIZED,
                    authException.getMessage(),
                    LocalDateTime.now()
            );

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(error));
        };
    }

    public AccessDeniedHandler accessDeniedHandler() {

        return (request, response, accessDeniedException) -> {
            ErrorResponseDTO error = new ErrorResponseDTO(
                    request.getRequestURI(),
                    HttpStatus.FORBIDDEN,
                    accessDeniedException.getMessage(),
                    LocalDateTime.now()
            );

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(error));
        };
    }


}
