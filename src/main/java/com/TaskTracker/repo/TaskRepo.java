package com.TaskTracker.repo;

import com.TaskTracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {
    //used to access the tasks table in the DB
}
