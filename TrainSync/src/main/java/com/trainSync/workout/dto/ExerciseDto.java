package com.trainSync.workout.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: Sajal Gupta Date: Nov 12, 2025
 */
public class ExerciseDto {
	private String id;
	private String name;
	private List<String> muscleTags;
	private List<SetDto> sets = new ArrayList<SetDto>();

	/**
	 * 
	 * @param id
	 * @param name
	 * @param muscleTags
	 */
	public ExerciseDto(String id, String name, List<String> muscleTags) {
		this.id = id;
		this.name = name;
		this.muscleTags = muscleTags;
	}

	public ExerciseDto(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public ExerciseDto(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<String> getMuscleTags() {
		return muscleTags;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMuscleTags(List<String> muscleTags) {
		this.muscleTags = muscleTags;
	}

	/**
	 * @return the sets
	 */
	public List<SetDto> getSets() {
		return sets;
	}

	/**
	 * @param sets the sets to set
	 */
	public void setSets(List<SetDto> sets) {
		this.sets = sets;
	}
}
