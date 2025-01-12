package org.example.tasklist.repository;

import org.example.tasklist.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByUserName(String userName);

    void update(User user);

    void create(User user);

    void insertUserRole(Long userId, Long roleId);

    boolean isTaskOwner(Long userId, Long taskId);

    void delete(User user);
}
