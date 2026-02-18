package com.marketplace.user_service.service.impl;

import com.marketplace.user_service.dto.LoginRequestDto;
import com.marketplace.user_service.dto.LoginResponseDto;
import com.marketplace.user_service.dto.UserCreateRequestDto;
import com.marketplace.user_service.dto.UserResponseDto;
import com.marketplace.user_service.entity.User;
import com.marketplace.user_service.exception.ResourceAlreadyExistException;
import com.marketplace.user_service.exception.ResourceNotFoundException;
import com.marketplace.user_service.mapper.AuthMapper;
import com.marketplace.user_service.mapper.UserMapper;
import com.marketplace.user_service.repository.UserRepo;
import com.marketplace.user_service.service.UserService;
import com.marketplace.user_service.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthMapper authMapper;

    public UserServiceImpl(UserRepo userRepo, UserMapper userMapper, PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil, AuthMapper authMapper){
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authMapper = authMapper;
    }

    @Override
    public UserResponseDto register(UserCreateRequestDto userCreateRequestDto) {
        if(userRepo.existByEmail(userCreateRequestDto.getEmail())){
            throw new ResourceAlreadyExistException("Email already exist");
        }
        User user = userMapper.toEntity(userCreateRequestDto);
        User savedUser = userRepo.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepo.findByEmail(loginRequestDto.getEmail()).orElseThrow(() ->
                new ResourceNotFoundException("User not found with this email: " + loginRequestDto.getEmail()));
        boolean matches = passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword());
        if(!matches){
            throw new ResourceNotFoundException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRoles());
        return authMapper.toLoginResponseDto(user, token);
    }

    @Override
    public UserResponseDto getById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }
}
