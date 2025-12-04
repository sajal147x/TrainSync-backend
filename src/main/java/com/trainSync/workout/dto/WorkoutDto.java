package com.trainSync.workout.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Date: Nov 13, 2025
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class WorkoutDto {
    private String exerciseId;
    private String workoutName;
    private String workoutDate;
    private String equipmentId;
    private String workoutId;
    private int exerciseOrder;
    
    private List<ExerciseDto> exercises = new ArrayList<ExerciseDto>();

  
}
