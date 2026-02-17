package com.marketplace.user_service.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String roles;
    private Instant createdAt;
    private Instant updatedAt;

}
