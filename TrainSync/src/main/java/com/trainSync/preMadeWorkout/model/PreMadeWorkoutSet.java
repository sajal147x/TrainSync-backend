
package com.trainSync.preMadeWorkout.model;

import java.util.UUID;

import jakarta.persistence.Column;
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
@Table(name = "pre_made_workout_set")
public class PreMadeWorkoutSet {

	@Id
	@GeneratedValue
	private UUID id;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pre_made_workout_exercise_id", nullable = false)
	PreMadeWorkoutExercise preMadeWorkoutExercise;
	
	@Column
	private int setNumber;

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
	 * @return the preMadeWorkoutExercise
	 */
	public PreMadeWorkoutExercise getPreMadeWorkoutExercise() {
		return preMadeWorkoutExercise;
	}

	/**
	 * @param preMadeWorkoutExercise the preMadeWorkoutExercise to set
	 */
	public void setPreMadeWorkoutExercise(PreMadeWorkoutExercise preMadeWorkoutExercise) {
		this.preMadeWorkoutExercise = preMadeWorkoutExercise;
	}
	
	/**
	 * @return the setNumber
	 */
	public int getSetNumber() {
		return setNumber;
	}

	/**
	 * @param setNumber the setNumber to set
	 */
	public void setSetNumber(int setNumber) {
		this.setNumber = setNumber;
	}

}
