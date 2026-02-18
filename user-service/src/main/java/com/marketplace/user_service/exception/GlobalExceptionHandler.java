package com.marketplace.user_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<Map<String, Object>> resourceAlreadyExistExceptionHandler(ResourceAlreadyExistException resourceAlreadyExistException){
        Map<String, Object> map = Map.of("error", "conflict", "message", resourceAlreadyExistException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> resourceNotFoundExceptionHandler(ResourceNotFoundException resourceNotFoundException){
        Map<String, Object> map = Map.of("Error", "Not found", "message", resourceNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String, Object> map = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(e -> map.put(e.getField(), e.getDefaultMessage()));
        Map<String, Object> body = Map.of("error", "Validation Failed", "details", map);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> runtimeException(RuntimeException runtimeException){
        Map<String, Object> map = Map.of("error", "Internal Server error", "message", runtimeException.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }


}
