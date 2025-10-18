package com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter;

import com.omarhammad.kdg_backend.restaurants.adapters.in.exceptions.InvalidCredentialsException;
import com.omarhammad.kdg_backend.restaurants.adapters.in.exceptions.WrongOpeningStatusValueException;
import com.omarhammad.kdg_backend.restaurants.adapters.out.exceptions.KeycloakServerException;
import com.omarhammad.kdg_backend.restaurants.adapters.out.exceptions.OwnerAlreadyExistInKeycloakException;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.ListIsEmptyException;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.NoDishesScheduledException;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.OrderBusinessRuleException;
import com.omarhammad.kdg_backend.restaurants.domain.exceptions.*;
import com.omarhammad.kdg_backend.restaurants.adapters.in.dto.generic.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFoundException(EntityNotFoundException exception,
                                                                          WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
    }

    @ExceptionHandler(InvalidUUIDFormatException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidUUIDFormatException(InvalidUUIDFormatException exception,
                                                                             WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    @ExceptionHandler(OwnerAlreadyHaveRestaurantException.class)
    public ResponseEntity<ErrorResponseDTO> handleBusinessRuleViolationException(OwnerAlreadyHaveRestaurantException exception,
                                                                                 WebRequest webRequest) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    @ExceptionHandler(InvalidEnumValueException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidEnumValueException(InvalidEnumValueException exception,
                                                                            WebRequest webRequest) {

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    @ExceptionHandler(ListIsEmptyException.class)
    public ResponseEntity<ErrorResponseDTO> handleListIsEmptyException(ListIsEmptyException exception,
                                                                       WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.NOT_FOUND,
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception,
                                                                                          WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(DishAlreadyPublishedException.class)
    public ResponseEntity<ErrorResponseDTO> handleDishAlreadyPublishedException(DishAlreadyPublishedException exception,
                                                                                WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(DishAlreadyUnPublishedException.class)
    public ResponseEntity<ErrorResponseDTO> handleDishAlreadyUnPublishedException(DishAlreadyUnPublishedException exception,
                                                                                  WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage(),
                        LocalDateTime.now()
                ));
    }


    @ExceptionHandler(NoDishesScheduledException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoDishesScheduledException(NoDishesScheduledException e,
                                                                             WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.BAD_REQUEST,
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(ScheduleTooSoonException.class)
    public ResponseEntity<ErrorResponseDTO> handleScheduleTooSoonException(ScheduleTooSoonException e,
                                                                           WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.BAD_REQUEST,
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(WrongOpeningStatusValueException.class)
    public ResponseEntity<ErrorResponseDTO> handleWrongOpeningStatusValueException(WrongOpeningStatusValueException e,
                                                                                   WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.BAD_REQUEST,
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentialsException(InvalidCredentialsException e,
                                                                              WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.UNAUTHORIZED,
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }


    @ExceptionHandler(OrderBusinessRuleException.class)
    public ResponseEntity<ErrorResponseDTO> handleOrderBusinessRuleException(OrderBusinessRuleException e,
                                                                                WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.BAD_REQUEST,
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }


    @ExceptionHandler(KeycloakServerException.class)
    public ResponseEntity<ErrorResponseDTO> handleKeycloakServerException(KeycloakServerException e,
                                                                          WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(OwnerAlreadyExistInKeycloakException.class)
    public ResponseEntity<ErrorResponseDTO> handleOwnerAlreadyExistInKeycloakException(OwnerAlreadyExistInKeycloakException e,
                                                                                       WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.BAD_REQUEST,
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalExceptions(Exception e,
                                                                   WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO(
                        webRequest.getDescription(false),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }
}
