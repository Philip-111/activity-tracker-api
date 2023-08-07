package com.example.activitytracker.services;

import com.example.activitytracker.Enums.Status;
import com.example.activitytracker.dto.requestDTO.TaskRequest;
import com.example.activitytracker.dto.responseDTO.TaskCreatedResponseDTO;
import com.example.activitytracker.dto.responseDTO.TaskDeleteDTO;
import com.example.activitytracker.dto.responseDTO.TaskStatusResponseDTO;
import com.example.activitytracker.dto.responseDTO.TaskUpdatedDTO;
import com.example.activitytracker.entities.AppUser;
import com.example.activitytracker.entities.Task;
import com.example.activitytracker.exception.myOwnException;
import com.example.activitytracker.repository.TaskRepository;
import com.example.activitytracker.repository.UserRepository;
import com.example.activitytracker.services.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskCreatedResponseDTO createTask(TaskRequest request, Long userid) {
        Optional<AppUser> optionalAppUser = userRepository.findById(userid);
        if (optionalAppUser.isEmpty()) {
            throw new myOwnException("Omo the person nor dey ooh");
        }

        AppUser appUser = optionalAppUser.get();

        Task newTask = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(Status.PENDING)
                .createdAt(LocalDateTime.now())
                .appUser(appUser)
                .build();

        Task savedTask = taskRepository.save(newTask);

        TaskCreatedResponseDTO responseDTO = TaskCreatedResponseDTO.builder()
                .id(savedTask.getId())
                .title(savedTask.getTitle())
                .description(savedTask.getDescription())
                .status(savedTask.getStatus())
                .createdAt(savedTask.getCreatedAt())
                .build();

        return responseDTO;
    }


    @Override
    public List<TaskCreatedResponseDTO> getAllTasks(Long userId) {
        Optional<AppUser> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new myOwnException("User not found", HttpStatus.NOT_FOUND);
        }
        AppUser user = userOptional.get();


        List<Task> tasks = taskRepository.findByAppUser(user);

        return tasks.stream()
                .map(task -> TaskCreatedResponseDTO.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .status(task.getStatus())
                        .createdAt(task.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskCreatedResponseDTO> getTasksByStatus(Status status, Long userId) {
        List<TaskCreatedResponseDTO> allTasks = getAllTasks(userId);
        return allTasks.stream()
                .filter(task -> task.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public TaskUpdatedDTO updateTask(Long taskId, TaskRequest request, Long userId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            throw new myOwnException("Omo the task nor dey oo", HttpStatus.NO_CONTENT);
        }
        Task existingTask = optionalTask.get();

        if (!existingTask.getAppUser().getId().equals(userId)) {
            throw new myOwnException("Access denied. Task does not belong to the user");
        }

        existingTask.setTitle(request.getTitle());
        existingTask.setDescription(request.getDescription());
        existingTask.setUpdatedAt(LocalDateTime.now());
        Task updatedTask = taskRepository.save(existingTask);

        return TaskUpdatedDTO.builder()
                .id(updatedTask.getId())
                .title(updatedTask.getTitle())
                .description(updatedTask.getDescription())
                .status(updatedTask.getStatus())
                .updated(updatedTask.getUpdatedAt())
                .build();
    }


    @Override
    public TaskDeleteDTO deleteTask(Long taskId, Long userId) {

        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()) {
            throw new myOwnException("task nor dey", HttpStatus.NO_CONTENT);
        }
        Optional<AppUser> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new myOwnException("the user not in the database");
        }
        Task existingTask = task.get();
        AppUser existingUser = user.get();

        if (!existingTask.getAppUser().getId().equals(existingUser.getId())) {
            throw new myOwnException("You are not eligible to delete the task", HttpStatus.FORBIDDEN);
        }
        taskRepository.delete(existingTask);
        return TaskDeleteDTO.builder()
                .id(existingTask.getId()).message(" Is Successfully deleted").build();
    }
    @Override
    public TaskStatusResponseDTO moveTaskByStatus(Status newStatus, Long userId, Long taskId) {
        Optional<AppUser> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new myOwnException("User not found", HttpStatus.NOT_FOUND);
        }
        AppUser user1 = user.get();

        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()) {
            throw new myOwnException("The task nor dey", HttpStatus.NOT_FOUND);
        }
        Task existingTask = task.get();

        if (!existingTask.getAppUser().getId().equals(user1.getId())) {
            throw new myOwnException("Not authorized to change status", HttpStatus.FORBIDDEN);
        }

        existingTask.setStatus(newStatus);
        if (newStatus == Status.DONE) {
            existingTask.setCompletedAt(LocalDateTime.now());
        }
        else{
            existingTask.setUpdatedAt(LocalDateTime.now());
        }

        taskRepository.save(existingTask);

        return TaskStatusResponseDTO.builder()
                .title(existingTask.getTitle())
                .description(existingTask.getDescription())
                .status(existingTask.getStatus())
                .updatedAt(existingTask.getUpdatedAt())
                .completedAt(existingTask.getCompletedAt())
                .build();
    }

    @Override
    public TaskCreatedResponseDTO getTaskById(Long taskId, Long userId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isEmpty()) {
            throw new myOwnException("Task not found", HttpStatus.NOT_FOUND);
        }
        Task task = taskOptional.get();

        if (!task.getAppUser().getId().equals(userId)) {
            throw new myOwnException("Access denied. Task does not belong to YOU",HttpStatus.FORBIDDEN);
        }
        TaskCreatedResponseDTO responseDTO = TaskCreatedResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .build();

        return responseDTO;
    }
}





