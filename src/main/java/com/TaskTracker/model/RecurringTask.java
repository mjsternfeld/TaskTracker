package com.TaskTracker.model;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Period;

//class that defines the recurringTasks datatype used in the recurring_tasks table
//these are automatically added as active to the normal task table, depending on the repeat interval and starting date

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecurringTask extends Task {

    private Period repeatInterval; // e.g. daily, weekly,...

    private LocalDateTime nextOccurrence; //or alternatively, the starting date

}

