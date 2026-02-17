package com.marketplace.user_service.service.impl;

import com.marketplace.user_service.dto.LoginRequestDto;
import com.marketplace.user_service.dto.LoginResponseDto;
import com.marketplace.user_service.dto.UserCreateRequestDto;
import com.marketplace.user_service.dto.UserResponseDto;
import com.marketplace.user_service.entity.User;
import com.marketplace.user_service.exception.DuplicateResourceException;
import com.marketplace.user_service.repository.UserRepo;
import com.marketplace.user_service.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public UserResponseDto register(UserCreateRequestDto userCreateRequestDto) {
        if(userRepo.existByEmail(userCreateRequestDto.getEmail())){
            throw new DuplicateResourceException(()-> "Email already exist");
        }
        User user = new User();
        user.setName(userCreateRequestDto.getName());
        user.setEmail(userCreateRequestDto.getEmail());
        user.setPassword(userCreateRequestDto.getPassword());
        User savedUser = userRepo.save(user);
        return savedUser;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        return null;
    }

    @Override
    public UserResponseDto getById(Long id) {
        return null;
    }
}
