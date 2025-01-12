package org.example.tasklist.repository;

import org.example.tasklist.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Optional<Task> findById(Long id);

    List<Task> findAllByUserId(Long userId);

    void assignToUserById(Long userId, Long taskId);

    void update(Task task);

    void create(Task task);

    void delete(Task task);
}
