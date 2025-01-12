package org.example.tasklist.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String userName;
    private String password;
    private String passwordConfirmation;
}
