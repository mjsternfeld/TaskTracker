package com.TaskTracker.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private TaskStatus status;

    @Column(nullable = false)
    private boolean isTemplate = false;


    //optional columns

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private Date deadline;

    //tasks can contain/consist of subtasks
    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = true)
    @OrderColumn(name = "subtask_order") //the order of subtasks is stored automatically in the subtask_order column
    @JsonManagedReference //this indicates this side of the relationship is the parent
    private List<Subtask> subtasks = new ArrayList<>();


}


