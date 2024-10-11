package com.TaskTracker.controller;

import com.TaskTracker.model.Subtask;
import com.TaskTracker.model.Task;
import com.TaskTracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    public TaskService service;

    //read

    @GetMapping("/get_tasks")
    public ResponseEntity<List<Task>> getAllTasks(){
        return ResponseEntity.ok(service.getAllTasks());
    }

    @GetMapping("/get_task{id}")
    public ResponseEntity<Task> getTaskByID(@PathVariable int id){
        Optional<Task> task = service.getTaskById(id);
        if(task.isPresent())
            return ResponseEntity.ok(task.get());
        else
            return ResponseEntity.notFound().build();
    }


    //create

    @PostMapping("/import_tasks")
    public ResponseEntity<?> importTasks(@RequestBody List<Task> tasks){
        List<Task> savedTasks = service.saveTasks(tasks);



        return ResponseEntity.ok(savedTasks); //return saved tasks as response
    }

    @PostMapping("/import_task")
    public ResponseEntity<?> importTasks(@RequestBody Task task){
        Task savedTask = service.saveTask(task);




        return ResponseEntity.ok(savedTask); //return saved tasks as response
    }


    //TODO: update

    @PutMapping("/update_task")
    public ResponseEntity<?> updateTask(@RequestBody Task task){
        Task updatedTask = service.updateTask(task);
        if(updatedTask == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(updatedTask);
    }



    //TODO: delete


}
