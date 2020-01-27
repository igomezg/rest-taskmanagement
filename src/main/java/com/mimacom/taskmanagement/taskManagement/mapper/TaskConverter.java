package com.mimacom.taskmanagement.taskManagement.mapper;

import com.mimacom.taskmanagement.taskManagement.dto.CreateTaskDto;
import com.mimacom.taskmanagement.taskManagement.dto.TaskDto;
import com.mimacom.taskmanagement.taskManagement.model.Task;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskConverter {

    final ModelMapper modelMapper;

    public TaskDto convertToDto(Task task){
        return modelMapper.map(task, TaskDto.class);
    }

    public Task converToEntity(TaskDto taskDto){
        return modelMapper.map(taskDto,Task.class);
    }

    public Task convertCreateDtoToEntity(CreateTaskDto createTaskDto){
        return modelMapper.map(createTaskDto, Task.class);
    }

}
