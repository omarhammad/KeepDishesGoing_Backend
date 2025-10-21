package com.omarhammad.kdg_backend.orders.adapters.in.webAdapters;

import com.omarhammad.kdg_backend.orders.adapters.in.dtos.generic.ErrorResponseDTO;
import com.omarhammad.kdg_backend.orders.adapters.in.exceptions.InvalidEnumValueException;
import com.omarhammad.kdg_backend.orders.core.exceptions.ClosedRestaurantException;
import com.omarhammad.kdg_backend.orders.core.exceptions.DishesFromDifferentRestaurantException;
import com.omarhammad.kdg_backend.orders.core.exceptions.EntityNotFoundException;
import com.omarhammad.kdg_backend.orders.core.exceptions.InvalidDishesException;
import com.omarhammad.kdg_backend.orders.domain.excptions.CustomerAlreadyExist;
import com.omarhammad.kdg_backend.orders.domain.excptions.OrderAlreadyPaidException;
import com.omarhammad.kdg_backend.orders.domain.excptions.PaymentNotSuccessfulException;
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
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(DishesFromDifferentRestaurantException.class)
    public ResponseEntity<ErrorResponseDTO> handleDishesFromDifferentRestaurantException(DishesFromDifferentRestaurantException exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.CONFLICT,
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(ClosedRestaurantException.class)
    public ResponseEntity<ErrorResponseDTO> handleClosedRestaurantException(ClosedRestaurantException exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.CONFLICT,
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(InvalidEnumValueException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidEnumValueException(InvalidEnumValueException exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(PaymentNotSuccessfulException.class)
    public ResponseEntity<ErrorResponseDTO> handlePaymentNotSuccessfulException(PaymentNotSuccessfulException exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.PAYMENT_REQUIRED,
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(OrderAlreadyPaidException.class)
    public ResponseEntity<ErrorResponseDTO> handleOrderAlreadyPaidException(OrderAlreadyPaidException exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.CONFLICT,
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }
    @ExceptionHandler(CustomerAlreadyExist.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomerAlreadyExist(CustomerAlreadyExist exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.CONFLICT,
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }


}
