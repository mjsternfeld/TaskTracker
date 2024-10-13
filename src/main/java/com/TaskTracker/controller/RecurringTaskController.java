package com.TaskTracker.controller;


import com.TaskTracker.model.RecurringTask;
import com.TaskTracker.service.RecurringTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recurringTasks")
public class RecurringTaskController {

    @Autowired
    private RecurringTaskService service;


    //create
    @PostMapping("/save_recTask")
    public ResponseEntity<RecurringTask> saveRecTask(@RequestBody RecurringTask rt){
        return ResponseEntity.ok(service.saveRecTask(rt));
    }

    //read
    @GetMapping("/get_recTasks")
    public ResponseEntity<List<RecurringTask>> getAllRecTasks(){
        return ResponseEntity.ok(service.getAllRecTasks());
    }

    @GetMapping("/get_recTask{id}")
    public ResponseEntity<RecurringTask> getRecTaskById(@PathVariable int id){
        return ResponseEntity.ok(service.getRecTaskById(id));
    }

    //update
    @PutMapping("/update_recTask")
    public ResponseEntity<RecurringTask> updateRecTask(RecurringTask rt){
        return ResponseEntity.ok(service.updateRecTask(rt));
    }

    //delete
    @DeleteMapping("/delete_recTask{id}")
    public ResponseEntity<?> deleteRecTask(@PathVariable int id){
        service.deleteRecTask(id);
        return ResponseEntity.ok().build();
    }

}
