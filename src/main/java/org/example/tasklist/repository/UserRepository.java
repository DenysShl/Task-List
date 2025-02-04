package org.example.tasklist.repository;

import org.example.tasklist.model.Role;
import org.example.tasklist.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String userName);

    void update(User user);

    void create(User user);

    void insertUserRole(Long userId, Role role);

    boolean isTaskOwner(Long userId, Long taskId);

    void delete(Long userId);
}
