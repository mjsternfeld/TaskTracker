package com.TaskTracker.repo;

import com.TaskTracker.model.Subtask;
import com.TaskTracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtaskRepo extends JpaRepository<Subtask, Integer> {

}
