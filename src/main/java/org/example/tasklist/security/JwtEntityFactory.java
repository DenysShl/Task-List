package org.example.tasklist.security;

import org.example.tasklist.model.Role;
import org.example.tasklist.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class JwtEntityFactory {

    public static JwtEntity create(final User user) {
        return new JwtEntity(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
        );
    }

    private static List<SimpleGrantedAuthority> mapToGrantedAuthorities(final List<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
