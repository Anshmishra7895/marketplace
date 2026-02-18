package com.marketplace.user_service.mapper;

import com.marketplace.user_service.dto.LoginResponseDto;
import com.marketplace.user_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public LoginResponseDto toLoginResponseDto(User user, String token){
        return new LoginResponseDto(token, "Bearer", user.getId(), user.getEmail(), user.getName());
    }

}
