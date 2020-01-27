package com.mimacom.taskmanagement.taskManagement.controller;

import com.mimacom.taskmanagement.taskManagement.dto.CreateTaskDto;
import com.mimacom.taskmanagement.taskManagement.dto.ErrorDto;
import com.mimacom.taskmanagement.taskManagement.dto.TaskDto;
import com.mimacom.taskmanagement.taskManagement.exceptions.TaskNotFoundException;
import com.mimacom.taskmanagement.taskManagement.service.TaskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping(value = "/taskmanagements/v1")
@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @ApiOperation(value="Find all the tasks")
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> searchAllTasks() {

        // For pagination
        int pages = 0;
        int size = 10;

        log.info("Searching for all the tasks in DB");

        List<TaskDto> listTasksDto = taskService.findAllTask(pages, size);
        return new ResponseEntity<>(listTasksDto, HttpStatus.OK);
    }


    @ApiOperation(value="Find one task by ID")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="OK", response=TaskDto.class),
            @ApiResponse(code=404, message="Not Found", response= ErrorDto.class),
            @ApiResponse(code=500, message="Internal Server Error", response=ErrorDto.class)
    })
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDto> searchTaskById(@PathVariable("id") Long id) {

        TaskDto p = taskService.findTaskById(id);

        if (p == null) throw new TaskNotFoundException(id.toString());

        return new ResponseEntity<TaskDto>(p, HttpStatus.OK);

    }

    @ApiOperation(value="Create a new Task", notes = "You can type name and comments about the task")
    @ApiResponses(value= {
        @ApiResponse(code=201, message="OK", response=TaskDto.class),
        @ApiResponse(code=400, message="Bad Request", response=ErrorDto.class),
        @ApiResponse(code=500, message="Internal Server Error", response=ErrorDto.class)})
    @PostMapping("/tasks")
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid CreateTaskDto taskDto) throws Exception {

        TaskDto t = taskService.createTask(taskDto);
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }

    @ApiOperation(value="Update a Task")
    @PutMapping("tasks/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable("id") Long id, @RequestBody CreateTaskDto taskDto) throws Exception {

        TaskDto t = taskService.updateTask(id, taskDto);
        return new ResponseEntity<>(t, HttpStatus.CREATED);

    }

    @ApiOperation(value="Finish a Task", notes = "Mark as finished one task with status=true and the actual date")
    @PatchMapping("tasks/{id}")
    public ResponseEntity<TaskDto> finishTask(@PathVariable("id") Long id) throws Exception {

        TaskDto t = taskService.finishTask(id);
        return new ResponseEntity<>(t, HttpStatus.CREATED);

    }

    @ApiOperation(value="Delete a Task by Id", notes = "it returns deleted object if the front needs information")
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<TaskDto> deletePartnerById(@PathVariable("id") Long id) throws Exception {
        TaskDto t = taskService.deleteTask(id);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }
}
