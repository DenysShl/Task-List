package org.example.tasklist.service;

import org.example.tasklist.model.User;

public interface UserService {
    User getById(Long id);

    User getByUsername(String userName);

    User update(User user);

    User create(User user);

    boolean isTaskOwner(Long userId, Long taskId);

    void delete(Long id);

    User getTaskAuthor(Long taskId);

}
