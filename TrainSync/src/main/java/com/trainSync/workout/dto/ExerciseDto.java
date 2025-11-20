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
	private List<EquipmentTagDto> equipmentTags = new ArrayList<EquipmentTagDto>();
	private String preFilledFlag;
	private String preFilledDate;
	private String preFilledWorkoutName;


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
	public List<EquipmentTagDto> getEquipmentTags() {
		return equipmentTags;
	}

	/**
	 * @param equipments the equipments to set
	 */
	public void setEquipmentTags(List<EquipmentTagDto> equipments) {
		this.equipmentTags = equipments;
	}
	
	/**
	 * @return the preFilledFlag
	 */
	public String getPreFilledFlag() {
		return preFilledFlag;
	}

	/**
	 * @param preFilledFlag the preFilledFlag to set
	 */
	public void setPreFilledFlag(String preFilledFlag) {
		this.preFilledFlag = preFilledFlag;
	}

	/**
	 * @return the preFilledDate
	 */
	public String getPreFilledDate() {
		return preFilledDate;
	}

	/**
	 * @param preFilledDate the preFilledDate to set
	 */
	public void setPreFilledDate(String preFilledDate) {
		this.preFilledDate = preFilledDate;
	}

	/**
	 * @return the preFilledWorkoutName
	 */
	public String getPreFilledWorkoutName() {
		return preFilledWorkoutName;
	}

	/**
	 * @param preFilledWorkoutName the preFilledWorkoutName to set
	 */
	public void setPreFilledWorkoutName(String preFilledWorkoutName) {
		this.preFilledWorkoutName = preFilledWorkoutName;
	}

}
