package org.example.tasklist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Task controller", description = "Task controller")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PutMapping()
    @Operation(summary = "Update task")
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto taskDto) {
        return taskMapper.toDto(
                taskService.update(
                        taskMapper.toModel(taskDto)
                )
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by id")
    public TaskDto getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task by id")
    public void deletedById(@PathVariable("id") Long id) {
        taskService.delete(id);
    }
}
