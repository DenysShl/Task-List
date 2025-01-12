package org.example.tasklist.dto;

import lombok.Data;
import org.example.tasklist.model.Status;

import java.time.LocalDateTime;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime expirationDate;
}
