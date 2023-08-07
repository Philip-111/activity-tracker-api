package com.example.activitytracker.controller;

import com.example.activitytracker.Enums.Status;
import com.example.activitytracker.dto.requestDTO.TaskRequest;
import com.example.activitytracker.dto.responseDTO.TaskCreatedResponseDTO;
import com.example.activitytracker.dto.responseDTO.TaskDeleteDTO;
import com.example.activitytracker.dto.responseDTO.TaskStatusResponseDTO;
import com.example.activitytracker.dto.responseDTO.TaskUpdatedDTO;
import com.example.activitytracker.exception.myOwnException;
import com.example.activitytracker.services.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TaskController {
    private final TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskRequest request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            throw new myOwnException("Please login to be able to create a task");
        }

        TaskCreatedResponseDTO createdTask = taskService.createTask(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }


    @GetMapping("/all")
    public ResponseEntity<List<TaskCreatedResponseDTO>> viewAllTasks(HttpSession session) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            throw new myOwnException("logged in login to get all task");
        }

        List<TaskCreatedResponseDTO> tasks = taskService.getAllTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status")
    public ResponseEntity<List<TaskCreatedResponseDTO>> getTasksByStatus(
            @RequestParam("status") Status status,
            HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            throw new myOwnException("Bros abeg try login before you can get this");
        }

        List<TaskCreatedResponseDTO> tasks = taskService.getTasksByStatus(status, userId);
        return ResponseEntity.ok(tasks);
    }



    @PatchMapping("/{taskId}")
    public ResponseEntity<?> editTask(@PathVariable Long taskId, @RequestBody TaskRequest updatedTask, HttpSession session) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            throw new myOwnException("User not logged in");
        }

        TaskUpdatedDTO task = taskService.updateTask(taskId, updatedTask, userId);
        return ResponseEntity.ok(task);
    }


    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            throw new myOwnException("Bros abeg try login");
        }

        TaskDeleteDTO response = taskService.deleteTask(taskId, userId);
        return ResponseEntity.status(HttpStatus.GONE).body(response);
    }

    @PostMapping("/status/{taskId}")
    public ResponseEntity<?> moveTaskStatus(@PathVariable Long taskId, HttpSession session, @RequestParam Status newStatus) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            throw new myOwnException("User not logged in");
        }

        TaskStatusResponseDTO task = taskService.moveTaskByStatus(newStatus, userId, taskId);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }


//    @PostMapping("/statusS/{userId}/{taskId}")
//    public ResponseEntity<?> moveTaskStatus(
//            @PathVariable Long taskId,
//            @PathVariable Long userId,
//            @RequestParam Status newStatus
//    ) {
//        TaskStatusResponseDTO task = taskService.moveTaskByStatus(newStatus, userId, taskId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(task);
//    }

//    @GetMapping("/{taskId}")
//    public ResponseEntity<TaskCreatedResponseDTO> getTaskById(@PathVariable Long taskId) {
//        TaskCreatedResponseDTO task = taskService.getTaskById(taskId);
//        return ResponseEntity.ok(task);
//    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskCreatedResponseDTO> viewParticularTask(@PathVariable Long taskId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userid");
        if (userId == null) {
            throw new myOwnException("User not logged in");
        }

        TaskCreatedResponseDTO task = taskService.getTaskById(taskId, userId);
        return ResponseEntity.ok(task);
    }



}
