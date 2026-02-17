package com.marketplace.user_service.service;

import com.marketplace.user_service.dto.LoginRequestDto;
import com.marketplace.user_service.dto.LoginResponseDto;
import com.marketplace.user_service.dto.UserResponseDto;
import com.marketplace.user_service.dto.UserCreateRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserResponseDto register(UserCreateRequestDto userCreateRequestDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    UserResponseDto getById(Long id);

}
