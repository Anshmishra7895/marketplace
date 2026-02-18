package com.marketplace.user_service.controller;

import com.marketplace.user_service.dto.LoginRequestDto;
import com.marketplace.user_service.dto.LoginResponseDto;
import com.marketplace.user_service.dto.UserCreateRequestDto;
import com.marketplace.user_service.dto.UserResponseDto;
import com.marketplace.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    public UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserCreateRequestDto dto){
        UserResponseDto userResponseDto = userService.register(dto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto){
        LoginResponseDto loginResponseDto = userService.login(dto);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id){
        UserResponseDto userResponseDto = userService.getById(id);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

}
