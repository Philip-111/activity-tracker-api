package com.example.activitytracker.dto.requestDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskRequest {
    private String title;
    private String description;
}
