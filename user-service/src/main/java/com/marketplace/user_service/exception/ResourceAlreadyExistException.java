package com.marketplace.user_service.exception;

public class ResourceAlreadyExistException extends RuntimeException {

    public ResourceAlreadyExistException(String  message) {
        super(message);
    }
}
