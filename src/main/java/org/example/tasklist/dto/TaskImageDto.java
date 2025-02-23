package org.example.tasklist.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TaskImageDto {
    @NotNull(message = "File must be not null!")
    private MultipartFile file;
}
