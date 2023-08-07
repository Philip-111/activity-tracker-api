package com.example.activitytracker.services.service;
import com.example.activitytracker.Enums.Status;
import com.example.activitytracker.dto.requestDTO.TaskRequest;
import com.example.activitytracker.dto.responseDTO.TaskCreatedResponseDTO;
import com.example.activitytracker.dto.responseDTO.TaskDeleteDTO;
import com.example.activitytracker.dto.responseDTO.TaskStatusResponseDTO;
import com.example.activitytracker.dto.responseDTO.TaskUpdatedDTO;

import java.util.List;

public interface TaskService {
    TaskCreatedResponseDTO createTask(TaskRequest request, Long userid);
    List<TaskCreatedResponseDTO> getAllTasks(Long userId);
    List<TaskCreatedResponseDTO> getTasksByStatus(Status status, Long userId);
    TaskUpdatedDTO updateTask(Long taskId, TaskRequest request, Long userId);
    TaskDeleteDTO deleteTask(Long taskId, Long userId);
    TaskStatusResponseDTO moveTaskByStatus(Status newStatus, Long userId, Long taskId);
    TaskCreatedResponseDTO getTaskById(Long taskId, Long userId);
}
