package org.example.tasklist.mapper;

import org.example.tasklist.dto.TaskDto;
import org.example.tasklist.model.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task task);

    List<TaskDto> toDto(List<Task> tasks);

    Task toModel(TaskDto taskDto);
}
