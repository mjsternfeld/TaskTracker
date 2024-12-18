package com.TaskTracker.service;

import com.TaskTracker.model.Subtask;
import com.TaskTracker.model.Task;
import com.TaskTracker.repo.SubtaskRepo;
import com.TaskTracker.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private SubtaskRepo subtaskRepo;

    //read

    //no templates! just "normal" tasks

    //returns all tasks associated with the user corresponding to the userId
    public List<Task> getAllTasks(String username){
        List<Task> unfilteredTasks = taskRepo.findAll();
        //filter with stream api
        return unfilteredTasks.stream()
                .filter(task -> !task.isTemplate() && task.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    public Optional<Task> getTaskById(int id) {
        return taskRepo.findById(id);
    }

    //no "normal" tasks, just templates
    //returns all templates associated with the user corresponding to the userId
    public List<Task> getAllTemplates(String username){
        List<Task> unfilteredTasks = taskRepo.findAll();
        //filter with stream api
        List<Task> nonTemplateTasks = unfilteredTasks.stream()
                .filter(task -> task.isTemplate() && task.getUsername().equals(username))
                .collect(Collectors.toList());
        return nonTemplateTasks;
    }



    //create
    //also associates task with the user
    public Task saveTask(Task task, String username){
        task.setUsername(username);
        //manually set subtask's references to their parent task
        for(Subtask subtask : task.getSubtasks())
            subtask.setParentTask(task);
        return taskRepo.save(task);
    }


    //update
    public Task updateTask(Task updatedTask) {
        //check if task is present in DB
        int id = updatedTask.getId();
        Optional<Task> taskOptional = taskRepo.findById(id);
        if (!taskOptional.isPresent())
            return null; //notFound response in controller
        //set the subtasks' references to their parent task (the updated subtasks, not yet added to the DB)
        if (updatedTask.getSubtasks() != null)
            for (Subtask subtask : updatedTask.getSubtasks())
                subtask.setParentTask(updatedTask);
        //get old task and delete the previous subtasks
        //the old subtasks don't matter, all the necessary subtasks are present in the updated version
        Task oldTask = taskOptional.get();
        oldTask.getSubtasks().clear();

        //overwrite the old task with the updated one
        //this overwrites the entire row, not attribute by attribute
        return taskRepo.save(updatedTask);
    }


    public void deleteTask(int id){
        taskRepo.deleteById(id);
    }





}
