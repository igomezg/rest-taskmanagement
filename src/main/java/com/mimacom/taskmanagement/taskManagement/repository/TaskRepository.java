package com.mimacom.taskmanagement.taskManagement.repository;

import com.mimacom.taskmanagement.taskManagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
