
package com.trainSync.preMadeWorkout.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Sajal Gupta Date: Nov 18, 2025
 */

public class PreMadeWorkoutFetchDto {

	private String id;
	private String name;
	private List<PreMadeWorkoutExerciseFetchDto> exercises = new ArrayList<>();

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
	 * @return the exercises
	 */
	public List<PreMadeWorkoutExerciseFetchDto> getExercises() {
		return exercises;
	}

	/**
	 * @param exercises the exercises to set
	 */
	public void setExercises(List<PreMadeWorkoutExerciseFetchDto> exercises) {
		this.exercises = exercises;
	}

}
