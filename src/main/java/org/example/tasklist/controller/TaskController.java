package org.example.tasklist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.tasklist.dto.TaskDto;
import org.example.tasklist.dto.TaskImageDto;
import org.example.tasklist.mapper.TaskImageMapper;
import org.example.tasklist.mapper.TaskMapper;
import org.example.tasklist.model.Task;
import org.example.tasklist.model.TaskImage;
import org.example.tasklist.service.TaskService;
import org.example.tasklist.validation.OnUpdate;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final TaskImageMapper taskImageMapper;

    @PutMapping()
    @MutationMapping(name = "updateTask")
    @Operation(summary = "Update task")
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody @Argument(name = "dto") TaskDto taskDto) {
        return taskMapper.toDto(
                taskService.update(
                        taskMapper.toModel(taskDto)
                )
        );
    }

    @GetMapping("/{id}")
    @QueryMapping(name = "taskById")
    @Operation(summary = "Get task by id")
    public TaskDto getById(@PathVariable @Argument(name = "id") Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @DeleteMapping("/{id}")
    @MutationMapping(name = "deleteTask")
    @Operation(summary = "Delete task by id")
    public void deletedById(@PathVariable("id") @Argument(name = "id") Long id) {
        taskService.delete(id);
    }

    @PostMapping("/{id}/image")
    @Operation(summary = "Upload image to task")
    @PreAuthorize("@customSecurityExpression.canAccessTask(#id)")
    public void uploadImage(@PathVariable Long id,
                            @Validated @ModelAttribute TaskImageDto imageDto) {
        TaskImage image = taskImageMapper.toModel(imageDto);
        taskService.uploadImage(id, image);
    }
}
