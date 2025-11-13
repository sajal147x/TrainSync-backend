package com.trainSync.workout.dto;

import java.util.List;
import java.util.UUID;

/**
 * Author: Sajal Gupta Date: Nov 12, 2025
 */
public class ExerciseDto {
	private UUID id;
	private String name;
	private List<String> muscleTags;

	/**
	 * 
	 * @param id
	 * @param name
	 * @param muscleTags
	 */
	public ExerciseDto(UUID id, String name, List<String> muscleTags) {
		this.id = id;
		this.name = name;
		this.muscleTags = muscleTags;
	}
	
	public ExerciseDto(String name) {
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<String> getMuscleTags() {
		return muscleTags;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMuscleTags(List<String> muscleTags) {
		this.muscleTags = muscleTags;
	}
}
