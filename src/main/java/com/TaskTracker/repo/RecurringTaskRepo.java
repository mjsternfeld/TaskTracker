package com.TaskTracker.repo;

import com.TaskTracker.model.RecurringTask;
import com.TaskTracker.model.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurringTaskRepo extends JpaRepository<RecurringTask, Integer> {
    //used to access the recurring_tasks table in the DB
}
