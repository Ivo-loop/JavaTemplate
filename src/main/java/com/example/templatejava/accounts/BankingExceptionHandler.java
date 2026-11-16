package com.example.templatejava.accounts;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BankingExceptionHandler {
    @ExceptionHandler(BankingException.class)
    public ResponseEntity<?> handle(BankingException exception) {
        if (exception.reason() == BankingException.Reason.INVALID_EVENT) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid event"));
        }
        if (exception.reason() == BankingException.Reason.INSUFFICIENT_FUNDS) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("details", "Insufficient funds"));
        }
        if (exception.reason() == BankingException.Reason.ACCOUNT_NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("details", "Account not found"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
    }
}
