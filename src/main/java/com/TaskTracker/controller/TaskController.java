package com.TaskTracker.controller;

import com.TaskTracker.model.Task;
import com.TaskTracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    public TaskService service;


    //create
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = service.saveTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask); // Return saved task with a 201 status
    }

    //read
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(service.getAllTasks());
    }

    @GetMapping("/templates")
    public ResponseEntity<List<Task>> getAllTemplates() {
        return ResponseEntity.ok(service.getAllTemplates());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        Optional<Task> task = service.getTaskById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    //update
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task task) {
        task.setId(id);
        Task updatedTask = service.updateTask(task);
        return updatedTask != null ? ResponseEntity.ok(updatedTask) : ResponseEntity.notFound().build();
    }


    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }
}
