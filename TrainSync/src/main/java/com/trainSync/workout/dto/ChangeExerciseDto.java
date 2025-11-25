
package com.trainSync.workout.dto;

/**
 * Author: Sajal Gupta Date: Nov 25, 2025
 */
public class ChangeExerciseDto {

	private String exerciseId;
	private String newExerciseLibraryId;
	private String workoutId;

	/**
	 * @return the exerciseId
	 */
	public String getExerciseId() {
		return exerciseId;
	}

	/**
	 * @param exerciseId the exerciseId to set
	 */
	public void setExerciseId(String exerciseId) {
		this.exerciseId = exerciseId;
	}


	/**
	 * @return the newExerciseLibraryId
	 */
	public String getNewExerciseLibraryId() {
		return newExerciseLibraryId;
	}

	/**
	 * @param newExerciseLibraryId the newExerciseLibraryId to set
	 */
	public void setNewExerciseLibraryId(String newExerciseLibraryId) {
		this.newExerciseLibraryId = newExerciseLibraryId;
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
