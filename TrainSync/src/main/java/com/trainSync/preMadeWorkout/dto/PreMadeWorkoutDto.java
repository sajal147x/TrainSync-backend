
package com.trainSync.preMadeWorkout.dto;

/**
 * Author: Sajal Gupta Date: Nov 18, 2025
 */

public class PreMadeWorkoutDto {

	private String preMadeWorkoutId;
	private String exerciseId;
	private String name;
	private String equipmentId;

	/**
	 * @return the exerciseId
	 */
	public String getExerciseId() {
		return exerciseId;
	}

	/**
	 * @return the preMadeWorkoutId
	 */
	public String getPreMadeWorkoutId() {
		return preMadeWorkoutId;
	}

	/**
	 * @param preMadeWorkoutId the preMadeWorkoutId to set
	 */
	public void setPreMadeWorkoutId(String preMadeWorkoutId) {
		this.preMadeWorkoutId = preMadeWorkoutId;
	}

	/**
	 * @param exerciseId the exerciseId to set
	 */
	public void setExerciseId(String exerciseId) {
		this.exerciseId = exerciseId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the equipmentId
	 */
	public String getEquipmentId() {
		return equipmentId;
	}

	/**
	 * @param equipmentId the equipmentId to set
	 */
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

}
