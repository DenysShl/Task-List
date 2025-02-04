package org.example.tasklist.controller;

import lombok.RequiredArgsConstructor;
import org.example.tasklist.dto.TaskDto;
import org.example.tasklist.mapper.TaskMapper;
import org.example.tasklist.model.Task;
import org.example.tasklist.service.TaskService;
import org.example.tasklist.validation.OnUpdate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PutMapping()
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto taskDto) {
        return taskMapper.toDto(
                taskService.update(
                        taskMapper.toModel(taskDto)
                )
        );
    }

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @DeleteMapping("/{id}")
    public void deletedById(@PathVariable("id") Long id) {
        taskService.delete(id);
    }
}
