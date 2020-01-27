package com.mimacom.taskmanagement.taskManagement.controller;

import com.mimacom.taskmanagement.taskManagement.TaskManagementApplication;
import com.mimacom.taskmanagement.taskManagement.dto.CreateTaskDto;
import com.mimacom.taskmanagement.taskManagement.dto.TaskDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = TaskManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port + "/taskmanagements/v1";
    }

    @Test
    public void searchAllTasks() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/tasks",
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
    }

    @Test
    public void searchTaskById() {
        TaskDto taskDto = restTemplate.getForObject(getRootUrl() + "/tasks/1",TaskDto.class);
        System.out.println(taskDto.getId());
        assertNotNull(taskDto);
    }

    @Test
    public void createTask() {
        CreateTaskDto createTaskDto = new CreateTaskDto();
        createTaskDto.setName("TEST");
        createTaskDto.setComments("Comments");

        ResponseEntity<CreateTaskDto> postResponse = restTemplate.postForEntity(getRootUrl() + "/tasks", createTaskDto, CreateTaskDto.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void updateTask() {

        Long id = 1L;
        TaskDto taskDto = restTemplate.getForObject(getRootUrl() + "/tasks/" + id, TaskDto.class);
         taskDto.setName("TEST2");
         taskDto.setComments("Comments2");
        restTemplate.put(getRootUrl() + "/tasks/" + id, taskDto);

        TaskDto updatedTask = restTemplate.getForObject(getRootUrl() + "/tasks/" + id, TaskDto.class);
        assertNotNull(updatedTask);
        assertEquals(updatedTask.getName(),"TEST2");

    }

    @Test
    public void deletePartnerById() {
        Long id = 1L;
        TaskDto taskDto = restTemplate.getForObject(getRootUrl() + "/tasks/" + id, TaskDto.class);
        assertNotNull(taskDto);

        restTemplate.delete(getRootUrl() + "/tasks/" + id);

        try {
            taskDto = restTemplate.getForObject(getRootUrl() + "/tasks/" + id, TaskDto.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
