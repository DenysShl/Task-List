package org.example.tasklist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.tasklist.dto.UserDto;
import org.example.tasklist.mapper.UserMapper;
import org.example.tasklist.model.User;
import org.example.tasklist.security.JwtRequest;
import org.example.tasklist.security.JwtResponse;
import org.example.tasklist.service.AuthService;
import org.example.tasklist.service.UserService;
import org.example.tasklist.validation.OnCreate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    @Operation(summary = "Login")
    public JwtResponse login(@Validated @RequestBody final JwtRequest jwtRequest) {
        return authService.login(jwtRequest);
    }

    @PostMapping("/register")
    @Operation(summary = "Register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody final UserDto userDto) {
        User user = userMapper.toModel(userDto);
        User createdUser = userService.create(user);
        return userMapper.toDto(createdUser);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh token")
    public JwtResponse refresh(@RequestBody final String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
