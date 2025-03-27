package org.example.tasklist.mapper;

import org.example.tasklist.dto.TaskDto;
import org.example.tasklist.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDto> {
}
