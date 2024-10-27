package com.study.verifyPayment.infrastructure.api;


import com.study.verifyPayment.domain.exceptions.DomainException;
import com.study.verifyPayment.domain.exceptions.NotFoundException;
import com.study.verifyPayment.domain.validation.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(final DomainException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(ex));
    }

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<?> handleDomainException(final DomainException ex) {
        return ResponseEntity.unprocessableEntity().body(ApiError.from(ex));
    }

    record ApiError(
            String message,
            List<Error> errors
    ) {
        static ApiError from(DomainException exception) {
            return new ApiError(exception.getMessage(), exception.getErrors());
        }
    }
}