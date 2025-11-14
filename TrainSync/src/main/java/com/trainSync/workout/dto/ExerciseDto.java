package com.trainSync.workout.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Sajal Gupta Date: Nov 12, 2025
 */
public class ExerciseDto {
	private String id;
	private String name;
	private List<MuscleTagDto> muscleTags;
	private List<SetDto> sets = new ArrayList<SetDto>();
	private List<EquipmentTagDto> equipments = new ArrayList<EquipmentTagDto>();


	public ExerciseDto(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public ExerciseDto(String name) {
		this.name = name;
	}

	/**
	 * 
	 */
	public ExerciseDto() {}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
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

	/**
	 * @return the muscleTags
	 */
	public List<MuscleTagDto> getMuscleTags() {
		return muscleTags;
	}

	/**
	 * @param muscleTags the muscleTags to set
	 */
	public void setMuscleTags(List<MuscleTagDto> muscleTags) {
		this.muscleTags = muscleTags;
	}

	/**
	 * @return the equipments
	 */
	public List<EquipmentTagDto> getEquipments() {
		return equipments;
	}

	/**
	 * @param equipments the equipments to set
	 */
	public void setEquipments(List<EquipmentTagDto> equipments) {
		this.equipments = equipments;
	}
}
