package com.mimacom.taskmanagement.taskManagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDto {

    @ApiModelProperty(value="ID de la Categoria", dataType = "Long", example="1", position=1)
    private Long id;
    @ApiModelProperty(value="Name of the task", dataType = "String", example="Call Tom", position=2)
    private String name;
    @ApiModelProperty(value="Additional info of the task", dataType = "String", example="To talk about the week planning", position=3)
    private String comments;
    @ApiModelProperty(value="Date of creation of the task, format yyyy-mm-dd", dataType = "LocalDate", example="2020-01-01", position=4)
    private LocalDate creationDate;
    @ApiModelProperty(value="Date when the tasks was finished, format yyyy-mm-dd", dataType = "LocalDate", example="2020-01-01", position=5)
    private LocalDate executionDate;
    @ApiModelProperty(value="Status of the task", dataType = "Boolean", example="false: if it's pending, true: if it's finished", position=6)
    private Boolean status;

}
