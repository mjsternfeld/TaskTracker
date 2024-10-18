package com.TaskTracker.controller;


import com.TaskTracker.model.RecurringTask;
import com.TaskTracker.service.RecurringTaskService;
import com.TaskTracker.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recurring-tasks")
public class RecurringTaskController {

    //this is used to handle requests related to recurring tasks (CRUD)

    @Autowired
    private RecurringTaskService service;

    @Autowired
    public JwtUtil jwtUtil;

    //helper method to extract username from JWT to allow filtering tasks by username
    public String getUsernameFromAuthHeader(String authHeader){
        String token = authHeader.substring(7); //remove "Bearer" prefix
        return jwtUtil.extractUsername(token);
    }

    //create
    @PostMapping
    public ResponseEntity<RecurringTask> createRecurringTask(@RequestBody RecurringTask rt, @RequestHeader("Authorization") String authHeader) {
        String username = getUsernameFromAuthHeader(authHeader); //add username to the task before saving in the DB
        RecurringTask savedTask = service.saveRecTask(rt, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask); // Returns 201 Created
    }

    //read
    //returns all recurring tasks associated with the user
    @GetMapping
    public ResponseEntity<List<RecurringTask>> getAllRecurringTasks(@RequestHeader("Authorization") String authHeader) {
        String username = getUsernameFromAuthHeader(authHeader);
        return ResponseEntity.ok(service.getAllRecTasks(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecurringTask> getRecurringTaskById(@PathVariable int id) {
        RecurringTask task = service.getRecTaskById(id);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build(); // Handle not found
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<RecurringTask> updateRecurringTask(@PathVariable int id, @RequestBody RecurringTask rt) {
        rt.setId(id);
        RecurringTask updatedTask = service.updateRecTask(rt);
        return updatedTask != null ? ResponseEntity.ok(updatedTask) : ResponseEntity.notFound().build(); // Handle not found
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecurringTask(@PathVariable int id) {
        service.deleteRecTask(id);
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }
}

