package com.marketplace.product_service.util;

public class ApiResponse {
    private String message;
    private Boolean status;

    public ApiResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }
}
