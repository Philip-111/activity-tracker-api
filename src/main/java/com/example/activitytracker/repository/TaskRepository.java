package com.example.activitytracker.repository;

import com.example.activitytracker.entities.AppUser;
import com.example.activitytracker.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task>findByAppUser(AppUser user);
}
