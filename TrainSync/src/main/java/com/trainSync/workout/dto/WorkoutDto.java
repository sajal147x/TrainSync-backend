package com.trainSync.workout.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Sajal Gupta
 * Date: Nov 13, 2025
 */
public class WorkoutDto {
    private String exerciseId;
    private String workoutName;
    private String workoutDate;
    
    private String workoutId;
    
    private List<ExerciseDto> exercises = new ArrayList<ExerciseDto>();

    public WorkoutDto() {}


    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }


	/**
	 * @return the workoutName
	 */
	public String getWorkoutName() {
		return workoutName;
	}


	/**
	 * @param workoutName the workoutName to set
	 */
	public void setWorkoutName(String workoutName) {
		this.workoutName = workoutName;
	}


	/**
	 * @return the workoutDate
	 */
	public String getWorkoutDate() {
		return workoutDate;
	}


	/**
	 * @param workoutDate the workoutDate to set
	 */
	public void setWorkoutDate(String workoutDate) {
		this.workoutDate = workoutDate;
	}


	/**
	 * @return the exercises
	 */
	public List<ExerciseDto> getExercises() {
		return exercises;
	}


	/**
	 * @param exercises the exercises to set
	 */
	public void setExercises(List<ExerciseDto> exercises) {
		this.exercises = exercises;
	}


	/**
	 * @return the workoutId
	 */
	public String getWorkoutId() {
		return workoutId;
	}


	/**
	 * @param workoutId the workoutId to set
	 */
	public void setWorkoutId(String workoutId) {
		this.workoutId = workoutId;
	}

  
}
