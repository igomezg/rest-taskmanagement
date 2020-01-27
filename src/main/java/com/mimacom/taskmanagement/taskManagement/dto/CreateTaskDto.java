package com.mimacom.taskmanagement.taskManagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTaskDto {
    @ApiModelProperty(value="Name of the task", dataType = "String", example="Call Tom", position=1)
    private String name;
    @ApiModelProperty(value="Additional info of the task", dataType = "String", example="To talk about the week planning", position=2)
    private String comments;
}
