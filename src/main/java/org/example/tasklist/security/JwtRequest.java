package org.example.tasklist.security;

import lombok.Data;

@Data
public class JwtRequest {
    private String userName;
    private String password;
}
