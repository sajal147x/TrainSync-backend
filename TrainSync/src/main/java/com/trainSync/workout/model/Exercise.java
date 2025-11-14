
package com.trainSync.workout.model;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Author: Sajal Gupta Date: Nov 11, 2025
 */
@Entity
@Table(name = "exercise")
public class Exercise {

	@Id
	@GeneratedValue
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "workout_id", nullable = false)
	private Workout workout;

	@Column
	private String name; // e.g. "Bench Press", "Squat"



	@OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ExerciseSet> sets;
	
	
	@Column
	private UUID exerciseLibraryId;
	
	@Column
	private UUID equipmentId;

	/**
	 * @return the equipmentId
	 */
	public UUID getEquipmentId() {
		return equipmentId;
	}

	/**
	 * @param equipmentId the equipmentId to set
	 */
	public void setEquipmentId(UUID equipmentId) {
		this.equipmentId = equipmentId;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the workout
	 */
	public Workout getWorkout() {
		return workout;
	}

	/**
	 * @param workout the workout to set
	 */
	public void setWorkout(Workout workout) {
		this.workout = workout;
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
	 * @return the sets
	 */
	public List<ExerciseSet> getSets() {
		return sets;
	}

	/**
	 * @param sets the sets to set
	 */
	public void setSets(List<ExerciseSet> sets) {
		this.sets = sets;
	}

	/**
	 * @return the exerciseLibraryId
	 */
	public UUID getExerciseLibraryId() {
		return exerciseLibraryId;
	}

	/**
	 * @param exerciseLibraryId the exerciseLibraryId to set
	 */
	public void setExerciseLibraryId(UUID exerciseLibraryId) {
		this.exerciseLibraryId = exerciseLibraryId;
	}

}
