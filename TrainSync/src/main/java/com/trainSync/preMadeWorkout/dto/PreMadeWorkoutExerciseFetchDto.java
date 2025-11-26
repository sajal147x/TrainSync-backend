package com.trainSync.preMadeWorkout.dto;

/**
 * Author: Sajal Gupta Date: Nov 18, 2025
 */

public class PreMadeWorkoutExerciseFetchDto {

	private String id;
	private String name;
	private String exercisePictureUrl;
	private int exerciseOrder;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the exercisePictureUrl
	 */
	public String getExercisePictureUrl() {
		return exercisePictureUrl;
	}

	/**
	 * @param exercisePictureUrl the exercisePictureUrl to set
	 */
	public void setExercisePictureUrl(String exercisePictureUrl) {
		this.exercisePictureUrl = exercisePictureUrl;
	}

	/**
	 * @return the exerciseOrder
	 */
	public int getExerciseOrder() {
		return exerciseOrder;
	}

	/**
	 * @param exerciseOrder the exerciseOrder to set
	 */
	public void setExerciseOrder(int exerciseOrder) {
		this.exerciseOrder = exerciseOrder;
	}

}
