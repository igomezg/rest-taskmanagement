package com.mimacom.taskmanagement.taskManagement.service;

import com.mimacom.taskmanagement.taskManagement.dto.CreateTaskDto;
import com.mimacom.taskmanagement.taskManagement.dto.TaskDto;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;


@Service
public interface TaskService {

    TaskDto createTask(@Valid CreateTaskDto createTaskDto);

    TaskDto updateTask(Long id, CreateTaskDto createTaskDto);

    List<TaskDto> findAllTask(int pages, int size);

    TaskDto findTaskById(Long id);

    TaskDto deleteTask(Long id);

    TaskDto finishTask(Long id);
}
