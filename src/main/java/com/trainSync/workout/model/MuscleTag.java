
package com.trainSync.workout.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Author: Sajal Gupta Date: Nov 12, 2025
 */
@Entity
@Table(name = "muscle_tag")
public class MuscleTag {

	@Id
	@GeneratedValue
	private UUID id;

	@Column(nullable = false, unique = true)
	private String name; // e.g. "Chest", "Legs", "Triceps"

	@Column
	private String muscleGroup;

	@Column
	private String musclePictureUrl;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the muscleGroup
	 */
	public String getMuscleGroup() {
		return muscleGroup;
	}

	/**
	 * @param muscleGroup the muscleGroup to set
	 */
	public void setMuscleGroup(String muscleGroup) {
		this.muscleGroup = muscleGroup;
	}

	/**
	 * @return the musclePictureUrl
	 */
	public String getMusclePictureUrl() {
		return musclePictureUrl;
	}

	/**
	 * @param musclePictureUrl the musclePictureUrl to set
	 */
	public void setMusclePictureUrl(String musclePictureUrl) {
		this.musclePictureUrl = musclePictureUrl;
	}
}
