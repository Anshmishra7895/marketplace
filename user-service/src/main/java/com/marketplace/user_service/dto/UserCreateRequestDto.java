package com.marketplace.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDto {

    @NotBlank(message = "Name required")
    private String name;

    @Email(message = "Valid Email Required")
    private String email;

    @NotBlank(message = "Password required")
    @Size(min = 5, message = "Password must be at least 5 character")
    private String password;
    private String phone;

}
