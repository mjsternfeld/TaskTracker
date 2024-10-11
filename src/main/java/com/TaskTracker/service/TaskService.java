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


    //TODO: update

    public List<Subtask> addSubtasks(List<Subtask> subtasks){
        return subtaskRepo.saveAll(subtasks);
    }



    //TODO: delete



}
