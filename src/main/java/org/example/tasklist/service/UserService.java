package org.example.tasklist.service;

import org.example.tasklist.model.User;

import java.util.Optional;

public interface UserService {
    User getById(Long id);

    User getByUsername(String userName);

    User update(User user);

    User create(User user);

    boolean isTaskOwner(Long userId, Long taskId);

    void delete(Long id);
}
