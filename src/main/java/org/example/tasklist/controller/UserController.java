package org.example.tasklist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.tasklist.dto.TaskDto;
import org.example.tasklist.dto.UserDto;
import org.example.tasklist.mapper.TaskMapper;
import org.example.tasklist.mapper.UserMapper;
import org.example.tasklist.service.TaskService;
import org.example.tasklist.service.UserService;
import org.example.tasklist.validation.OnCreate;
import org.example.tasklist.validation.OnUpdate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "User operations")
public class UserController {
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;
    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public UserDto getById(@PathVariable("/{id}") Long id) {
        return userMapper.toDto(
                userService.getById(id)
        );
    }

    @PutMapping
    @Operation(summary = "Update user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#userDto.id)")
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
        return userMapper.toDto(
                userService.update(
                        userMapper.toModel(userDto)
                )
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public void deleteById(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}/tasks")
    @Operation(summary = "Get tasks by user id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public List<TaskDto> getTasksByUsedId(@PathVariable Long id) {
        return taskMapper.toDto(
                taskService.getAllByUserId(id)
        );
    }

    @PostMapping("/{id}/tasks")
    @Operation(summary = "Create task by user id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public TaskDto createTask(@PathVariable Long id,
                              @Validated(OnCreate.class) @RequestBody TaskDto taskDto) {
        return taskMapper.toDto(
                taskService.create(
                        taskMapper.toModel(taskDto), id
                )
        );
    }
}
