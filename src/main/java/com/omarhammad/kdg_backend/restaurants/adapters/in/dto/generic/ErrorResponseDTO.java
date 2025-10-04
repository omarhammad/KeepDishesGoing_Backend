package com.omarhammad.kdg_backend.restaurants.adapters.in.dto.generic;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponseDTO(   String apiPath,
         HttpStatus errorCode,
         String errorMessage,
         LocalDateTime errorTime) {

}
