package org.example.tasklist.mapper;

import org.example.tasklist.dto.TaskImageDto;
import org.example.tasklist.model.TaskImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskImageMapper extends Mappable<TaskImage, TaskImageDto> {
}
