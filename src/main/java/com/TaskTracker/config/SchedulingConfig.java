package com.TaskTracker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulingConfig {
    //this is used for the recurringTasks feature, so that they can be added automatically when they're due
}
