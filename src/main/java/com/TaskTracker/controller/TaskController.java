package com.TaskTracker.controller;

import com.TaskTracker.model.Task;
import com.TaskTracker.service.TaskService;
import com.TaskTracker.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    //this is used to handle requests related to (normal) tasks (and subtasks, but only in relation to normal tasks) and task templates (CRUD)

    @Autowired
    public TaskService service;

    @Autowired
    public JwtUtil jwtUtil;


    //helper method to extract username from JWT
    public String getUsernameFromAuthHeader(String authHeader){
        String token = authHeader.substring(7); //remove "Bearer" prefix
        return jwtUtil.extractUsername(token);
    }


    //create
    //creates a row in the tasks (and subtasks, if applicable) table,
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestHeader("Authorization") String authHeader) {
        String username = getUsernameFromAuthHeader(authHeader);
        Task savedTask = service.saveTask(task, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask); // Return saved task with a 201 status
    }

    //read
    //returns all tasks associated with the user
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@RequestHeader("Authorization") String authHeader) {
        String username = getUsernameFromAuthHeader(authHeader);
        return ResponseEntity.ok(service.getAllTasks(username));
    }

    //returns all templates associated with the user
    @GetMapping("/templates")
    public ResponseEntity<List<Task>> getAllTemplates(@RequestHeader("Authorization") String authHeader) {
        String username = getUsernameFromAuthHeader(authHeader);
        return ResponseEntity.ok(service.getAllTemplates(username));
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
