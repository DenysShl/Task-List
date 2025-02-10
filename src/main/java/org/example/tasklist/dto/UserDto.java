package org.example.tasklist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.tasklist.validation.OnCreate;
import org.example.tasklist.validation.OnUpdate;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(description = "User data transfer object")
public class UserDto {
    @Schema(description = "id", example = "1")
    @NotNull(message = "Id must be not null!", groups = OnUpdate.class)
    private Long id;

    @Schema(description = "name", example = "John Doe")
    @NotNull(message = "Name must be not null!", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Name length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Schema(description = "username", example = "johndoe@gmail.com")
    @NotNull(message = "Username must be not null!", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Username length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @Schema(description = "User crypted password", example = "$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null!", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @Schema(description = "User crypted password confirmation", example = "$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password confirmation must be not null!", groups = OnCreate.class)
    private String passwordConfirmation;
}
