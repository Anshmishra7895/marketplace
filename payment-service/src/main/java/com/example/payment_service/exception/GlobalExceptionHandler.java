package com.example.payment_service.exception;

import com.example.payment_service.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ApiResponse> paymentNotFoundException(PaymentNotFoundException paymentNotFoundException){
        return new ResponseEntity<>(new ApiResponse<>(
                false, "Payment Not Found Exception", paymentNotFoundException.getMessage(), LocalDateTime.now()
        ), HttpStatus.NOT_FOUND);
    }

}
