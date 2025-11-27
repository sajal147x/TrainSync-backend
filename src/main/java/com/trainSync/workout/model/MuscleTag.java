
package com.trainSync.workout.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: Sajal Gupta Date: Nov 12, 2025
 */
@Getter
@Setter
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

}
