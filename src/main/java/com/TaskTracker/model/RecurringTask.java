package com.TaskTracker.model;

import com.TaskTracker.util.DurationDeserializer;
import com.TaskTracker.util.DurationSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Period;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecurringTask extends Task {

    //@JsonSerialize(using = DurationSerializer.class)
    //@JsonDeserialize(using = DurationDeserializer.class)
    private Period repeatInterval; // e.g. daily, weekly,...

    private LocalDateTime nextOccurrence; //or alternatively, the starting date

}

