package com.TaskTracker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Subtask {

    //required columns

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subtaskId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "parent_task_id", nullable = false) //Foreign key to Task. Needs to be set manually.
    @JsonBackReference //this indicates this side of the relationship is the child
    private Task parentTask;



    //optional columns

    @Column(nullable = true)
    private String description;


}
