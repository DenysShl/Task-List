package org.example.tasklist.service;

import org.example.tasklist.security.JwtRequest;
import org.example.tasklist.security.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest jwtRequest);

    JwtResponse refresh(String refreshToken);
}
