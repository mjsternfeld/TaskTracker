package com.TaskTracker.service;

import com.TaskTracker.model.Subtask;
import com.TaskTracker.model.Task;
import com.TaskTracker.repo.SubtaskRepo;
import com.TaskTracker.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;
    private SubtaskRepo subtaskRepo;

    //read

    public List<Task> getAllTasks(){
        return taskRepo.findAll();
    }

    public Optional<Task> getTaskById(int id) {
        return taskRepo.findById(id);
    }


    //create

    public Task saveTask(Task task){

        //manually set subtask's references to their parent task
        for(Subtask subtask : task.getSubtasks())
            subtask.setParentTask(task);

        return taskRepo.save(task);
    }

    public List<Task> saveTasks(List<Task> tasks){

        //manually set subtask's references to their parent tasks
        for(Task task : tasks)
            for(Subtask subtask : task.getSubtasks())
                subtask.setParentTask(task);

        return taskRepo.saveAll(tasks);
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




    //TODO: delete

    public void deleteTask(int id){
        taskRepo.deleteById(id);
    }





}
