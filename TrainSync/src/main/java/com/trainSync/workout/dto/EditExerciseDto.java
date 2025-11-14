
package com.trainSync.workout.dto;

import java.util.UUID;

/**
 * Author: Sajal Gupta Date: Nov 13, 2025
 */

public class EditExerciseDto {
	private String exerciseId;
	private String setId; // optional for add-set
	private double weight;
	private int reps;
	private int setNumber;

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
	 * @return the setId
	 */
	public String getSetId() {
		return setId;
	}

	/**
	 * @param setId the setId to set
	 */
	public void setSetId(String setId) {
		this.setId = setId;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the reps
	 */
	public int getReps() {
		return reps;
	}

	/**
	 * @param reps the reps to set
	 */
	public void setReps(int reps) {
		this.reps = reps;
	}

	/**
	 * @return the setNumber
	 */
	public int getSetNumber() {
		return setNumber;
	}

	/**
	 * @param setNumber the setNumber to set
	 */
	public void setSetNumber(int setNumber) {
		this.setNumber = setNumber;
	}

}
