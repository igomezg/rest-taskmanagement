package com.mimacom.taskmanagement.taskManagement.service;

import com.mimacom.taskmanagement.taskManagement.dto.CreateTaskDto;
import com.mimacom.taskmanagement.taskManagement.dto.TaskDto;
import com.mimacom.taskmanagement.taskManagement.exceptions.TaskAnyNotFoundException;
import com.mimacom.taskmanagement.taskManagement.exceptions.TaskBadReqException;
import com.mimacom.taskmanagement.taskManagement.exceptions.TaskNotFoundException;
import com.mimacom.taskmanagement.taskManagement.mapper.TaskConverter;
import com.mimacom.taskmanagement.taskManagement.model.Task;
import com.mimacom.taskmanagement.taskManagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    final TaskConverter taskConverter;

    @Override
    public List<TaskDto> findAllTask(int page, int size) {

        log.info("Search for all the tasks");
        try {
            PageRequest pageReq = PageRequest.of(page, size);

            //Search all tasks
            Page<Task> tasks = taskRepository.findAll(pageReq);

            if (tasks.isEmpty()) {
                throw new TaskAnyNotFoundException();
            }

            //Return the list of partnersDto
            return tasks.stream().map(taskConverter::convertToDto).collect(Collectors.toList());
        } catch (TaskAnyNotFoundException rse) {

            throw new TaskAnyNotFoundException();
        } catch (Exception ex) {
            throw new TaskBadReqException();
        }
    }

    @Override
    public TaskDto findTaskById(Long id) {

        log.info("Search the task {}", id);
        try {
            Optional<Task> t = taskRepository.findById(id);
            if (t.isEmpty()) {
                throw new TaskNotFoundException(Long.toString(id));
            }

            return taskConverter.convertToDto(t.get());
        } catch (TaskNotFoundException te) {
            throw new TaskNotFoundException(Long.toString(id));
        } catch (Exception ex) {
            throw new TaskBadReqException();
        }
    }


    @Override
    public TaskDto deleteTask(Long id) {

        log.info("Delete the task {}", id);
        try {
            Task t = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(Long.toString(id)));
            taskRepository.delete(t);

            return taskConverter.convertToDto(t);
        } catch (TaskNotFoundException te) {
            throw new TaskNotFoundException(Long.toString(id));
        } catch (Exception ex) {
            throw new TaskBadReqException();
        }

    }

    @Override
    public TaskDto createTask(@Valid CreateTaskDto createTaskDto) {

        log.info("Create a new task");
        try {
            //We create a new object with front's values and set default values for a new entry
            Task taskNew = taskConverter.convertCreateDtoToEntity(createTaskDto);
            taskNew.setStatus(false);
            taskNew.setCreationDate(LocalDate.now());
            taskNew.setExecutionDate(null);

            return taskConverter
                    .convertToDto(taskRepository
                            .save(taskNew));
        } catch (Exception ex) {
            throw new TaskBadReqException();
        }
    }

    @Override
    public TaskDto updateTask(Long id, CreateTaskDto taskDto) {

        try {
            log.info("Update the task {} with the values {}", id, taskDto.toString());
            return taskConverter.convertToDto(taskRepository.findById(id).map(t -> {
                t.setName(taskDto.getName());
                t.setComments(taskDto.getComments());
                return taskRepository.save(t);
            }).orElseThrow(() -> new TaskNotFoundException(Long.toString(id))));
        } catch (TaskNotFoundException te) {
            throw new TaskNotFoundException(Long.toString(id));
        } catch (Exception ex) {
            throw new TaskBadReqException();
        }
    }

    @Override
    public TaskDto finishTask(Long id) {

        try {
            log.info("Finish the task {}", id);
            return taskConverter.convertToDto(taskRepository.findById(id).map(t -> {
                t.setStatus(true);
                t.setExecutionDate(LocalDate.now());
                return taskRepository.save(t);
            }).orElseThrow(() -> new TaskNotFoundException(Long.toString(id))));
        } catch (TaskNotFoundException te) {
            throw new TaskNotFoundException(Long.toString(id));
        } catch (Exception ex) {
            throw new TaskBadReqException();
        }
    }
}
