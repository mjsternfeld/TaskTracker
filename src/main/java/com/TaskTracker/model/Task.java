package com.TaskTracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    private int id;

    private String title;
    private String description;
    private TaskStatus status;
    private Date completionDate;

    //task hierarchy - tasks can contain/consist of subtasks
    @OneToMany
    private List<Task> subTasks;
    @ManyToOne
    private Task parentTask;


}


