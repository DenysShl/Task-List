package org.example.tasklist.security;

import lombok.Data;

@Data
public class JwtResponse {
    private Long id;
    private String userName;
    private String accessToken;
    private String refreshToken;
}
