package com.mimacom.taskmanagement.taskManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "taskSeqGen")
    @SequenceGenerator(name = "taskSeqGen", sequenceName = "task_seq", allocationSize = 1)
    @NotNull
    private Long id;

    @Column
    @NotNull
    private String name;

    @Column
    private String comments;

    @Column
    private LocalDate creationDate;

    @Column
    private LocalDate executionDate;

    @Column
    private Boolean status;


}
