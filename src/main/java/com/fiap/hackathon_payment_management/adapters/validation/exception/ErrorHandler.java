package com.fiap.hackathon_payment_management.adapters.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> errorHandler400(MethodArgumentNotValidException exception) {
        var errors = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(DataErrorValidation::new).toList());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> businessRuleError(ValidationException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageError("500", exception.getMessage()));
    }

    @ExceptionHandler(ValidationLimitCardException.class)
    public ResponseEntity<?> businessRuleError(ValidationLimitCardException exception) {
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(new MessageError("402", exception.getMessage()));
    }

    private record MessageError(String message, String errorCode) {
    }

    private record DataErrorValidation(String campo, String message) {
        public DataErrorValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

}
