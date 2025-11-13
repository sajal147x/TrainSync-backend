
package com.trainSync.workout.dto;

import java.time.LocalDateTime;

/**
 * Author: Sajal Gupta
 * Date: Nov 13, 2025
 */

public class RecentWorkoutDto {
	
	public RecentWorkoutDto(String workoutId, String workoutName, String workoutDate) {
		this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.workoutDate = workoutDate;
	}
	
	private String workoutId;
    private String workoutName;
    private String workoutDate;
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

}
