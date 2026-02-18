package com.marketplace.user_service.mapper;

import com.marketplace.user_service.dto.UserCreateRequestDto;
import com.marketplace.user_service.dto.UserResponseDto;
import com.marketplace.user_service.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(UserCreateRequestDto dto){
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .phone(dto.getPhone())
                .roles("ROLE_USER")
                .build();
    }

    public UserResponseDto toDto(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .roles(user.getRoles())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }


}
