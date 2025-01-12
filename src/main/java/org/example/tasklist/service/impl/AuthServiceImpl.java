package org.example.tasklist.service.impl;

import org.example.tasklist.security.JwtRequest;
import org.example.tasklist.security.JwtResponse;
import org.example.tasklist.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public JwtResponse login(JwtRequest jwtRequest) {
        return null;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }
}
