
package com.trainSync.preMadeWorkout.model;

import java.util.UUID;

import com.trainSync.workout.model.EquipmentTag;
import com.trainSync.workout.model.ExerciseLibrary;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Author: Sajal Gupta Date: Nov 17, 2025
 */
@Entity
@Table(name = "pre_made_workout_exercise")
public class PreMadeWorkoutExercise {

	@Id
	@GeneratedValue
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pre_made_workout_id", nullable = false)
	private PreMadeWorkout preMadeWorkout;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exercise_id", nullable = false)
	private ExerciseLibrary exercise;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipment_id", nullable = false)
	private EquipmentTag equipment;

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
	 * @return the preMadeWorkout
	 */
	public PreMadeWorkout getPreMadeWorkout() {
		return preMadeWorkout;
	}

	/**
	 * @param preMadeWorkout the preMadeWorkout to set
	 */
	public void setPreMadeWorkout(PreMadeWorkout preMadeWorkout) {
		this.preMadeWorkout = preMadeWorkout;
	}

	/**
	 * @return the exercise
	 */
	public ExerciseLibrary getExercise() {
		return exercise;
	}

	/**
	 * @param exercise the exercise to set
	 */
	public void setExercise(ExerciseLibrary exercise) {
		this.exercise = exercise;
	}

	/**
	 * @return the equipment
	 */
	public EquipmentTag getEquipment() {
		return equipment;
	}

	/**
	 * @param equipment the equipment to set
	 */
	public void setEquipment(EquipmentTag equipment) {
		this.equipment = equipment;
	}

}
