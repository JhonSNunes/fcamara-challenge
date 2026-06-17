package com.fcamara.fcamara_challenge.infrastructure.api.exception;

import com.fcamara.fcamara_challenge.domain.exception.AuthorizationException;
import com.fcamara.fcamara_challenge.domain.exception.CardAlreadyExistsException;
import com.fcamara.fcamara_challenge.domain.exception.CardNotFoundException;
import com.fcamara.fcamara_challenge.infrastructure.api.dto.CardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<String> handleAuthorizationException(AuthorizationException exception) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(exception.getErrorCode().getMessage());
    }

    @ExceptionHandler(CardAlreadyExistsException.class)
    public ResponseEntity<CardResponse> handleCardAlreadyExistsException(CardAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new CardResponse(exception.getCardNumber(), exception.getPassword()));
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<Void> handleCardNotFoundException() {
        return ResponseEntity.notFound().build();
    }
}
