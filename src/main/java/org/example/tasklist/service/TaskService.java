package org.example.tasklist.service;

import org.example.tasklist.model.Task;
import org.example.tasklist.model.TaskImage;

import java.util.List;

public interface TaskService {
    Task getById(Long id);

    List<Task> getAllByUserId(Long userId);

    Task update(Task task);

    Task create(Task task, Long userId);

    void delete(Long id);

    void uploadImage(Long id, TaskImage image);
}
