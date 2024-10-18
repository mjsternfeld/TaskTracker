package com.TaskTracker.model;

//this is an attribute of tasks, defined as an enum to allow for more control / freedom compared to the standard approach with a "completed" boolean

public enum TaskStatus {
    inactive,
    active,
    completed,
    failed,
    notRelevantAnymore
}
