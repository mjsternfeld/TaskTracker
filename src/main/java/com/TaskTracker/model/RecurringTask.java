package com.TaskTracker.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecurringTask extends Task {

    private Duration repeatInterval; // e.g. daily, weekly,...
    private LocalDateTime nextOccurrence; //or alternatively, the starting date

}

