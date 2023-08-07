package com.example.activitytracker.dto.responseDTO;

import com.example.activitytracker.Enums.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class TaskStatusResponseDTO {
    private String title;
    private String description;
    private Status status;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;


}
