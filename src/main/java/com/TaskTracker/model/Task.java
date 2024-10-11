package com.TaskTracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    //required columns

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private TaskStatus status;



    //optional columns

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private Date deadline;

    //tasks can contain/consist of subtasks
    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = true)
    @OrderColumn(name = "subtask_order") // Stores the order in a separate column
    private List<Subtask> subtasks = new ArrayList<>();


}


