package com.mimacom.taskmanagement.taskManagement;

import com.mimacom.taskmanagement.taskManagement.controller.TaskController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskManagementApplicationTests {

	@Autowired
	TaskController taskController;

	@Test
	public void contextLoads() {
		Assert.notNull(taskController);
	}

}
