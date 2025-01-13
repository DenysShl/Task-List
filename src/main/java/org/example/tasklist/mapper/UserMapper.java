package org.example.tasklist.mapper;

import org.example.tasklist.dto.UserDto;
import org.example.tasklist.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toModel(UserDto userDto);
}
