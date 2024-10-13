package com.TaskTracker.service;

import com.TaskTracker.model.Task;
import com.TaskTracker.model.TaskStatus;
import com.TaskTracker.repo.RecurringTaskRepo;
import com.TaskTracker.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.TaskTracker.model.RecurringTask;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RecurringTaskService {

    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private RecurringTaskRepo recTaskRepo;

    @Scheduled(fixedRate = 3600000)  //1h in ms
    public void processRecurringTasks() {

        List<RecurringTask> unfilteredTasks = recTaskRepo.findAll();
        //filter by nextOccurence [before] now, i.e., all the tasks that should've been started by now
        List<RecurringTask> recurringTasks = unfilteredTasks.stream()
                .filter(recurringTask -> LocalDateTime.now().isAfter(recurringTask.getNextOccurrence()))
                .toList();

        for (RecurringTask recurringTask : recurringTasks) {
            //create new Task based on the RecurringTask
            Task newTask = new Task();
            newTask.setTitle(recurringTask.getTitle());
            newTask.setDescription(recurringTask.getDescription());
            newTask.setSubtasks(recurringTask.getSubtasks());
            newTask.setStatus(TaskStatus.active);

            //converting LocalDateTime to Date
            Date convertedDate = Date.from(recurringTask.getNextOccurrence()
                    .atZone(ZoneId.systemDefault()).toInstant());

            newTask.setDeadline(convertedDate);

            //save finished Task
            taskRepo.save(newTask);

            //update next occurrence
            LocalDateTime nextOccurrence = recurringTask.getNextOccurrence().plus(recurringTask.getRepeatInterval());
            recurringTask.setNextOccurrence(nextOccurrence);
            recTaskRepo.save(recurringTask);
        }
    }


    //create
    public RecurringTask saveRecTask(RecurringTask rt){
        return recTaskRepo.save(rt);
    }

    //read
    public List<RecurringTask> getAllRecTasks() {
        return recTaskRepo.findAll();
    }

    public RecurringTask getRecTaskById(int id) {
        Optional<RecurringTask> optRecTask = recTaskRepo.findById(id);
        if(optRecTask.isEmpty())
            return null;
        return optRecTask.get();
    }

    //update
    public RecurringTask updateRecTask(RecurringTask rt){
        Optional<RecurringTask> optRt = recTaskRepo.findById(rt.getId());
        if(optRt.isEmpty())
            return null; //notFound response in controller
        return recTaskRepo.save(rt);
    }

    //delete
    public void deleteRecTask(int id){
        taskRepo.deleteById(id);
    }


}
