package com.omarhammad.kdg_backend.orders.adapters.in.webAdapters;

import com.omarhammad.kdg_backend.orders.adapters.in.dtos.generic.ErrorResponseDTO;
import com.omarhammad.kdg_backend.orders.core.exceptions.ClosedRestaurantException;
import com.omarhammad.kdg_backend.orders.core.exceptions.DishesFromDifferentRestaurantException;
import com.omarhammad.kdg_backend.orders.core.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.orders.core.exceptions.InvalidDishesException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class OrderGlobalExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(InvalidDishesException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidDishesException(InvalidDishesException exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(DishesFromDifferentRestaurantException.class)
    public ResponseEntity<ErrorResponseDTO> handleDishesFromDifferentRestaurantException(DishesFromDifferentRestaurantException exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(ClosedRestaurantException.class)
    public ResponseEntity<ErrorResponseDTO> handleClosedRestaurantException(ClosedRestaurantException exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

}
