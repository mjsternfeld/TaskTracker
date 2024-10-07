package com.TaskTracker.service;

import com.TaskTracker.model.Task;
import com.TaskTracker.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepo repo;

    public List<Task> getAllTasks(){
        return repo.findAll();
    }

}
