
package com.trainSync.preMadeWorkout.model;

import java.util.UUID;

import com.trainSync.workout.model.ExerciseLibrary;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: Sajal Gupta Date: Nov 17, 2025
 */
@Getter
@Setter
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

	@Column
	private int exerciseOrder;



}
