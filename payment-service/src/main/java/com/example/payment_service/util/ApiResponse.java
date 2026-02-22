package com.example.payment_service.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T> {

    private Boolean status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

}
