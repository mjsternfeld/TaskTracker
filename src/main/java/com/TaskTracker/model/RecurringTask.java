package com.TaskTracker.model;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Period;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecurringTask extends Task {

    private Period repeatInterval; // e.g. daily, weekly,...

    private LocalDateTime nextOccurrence; //or alternatively, the starting date

}

