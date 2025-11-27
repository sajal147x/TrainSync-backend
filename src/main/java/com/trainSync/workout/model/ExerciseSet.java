
package com.trainSync.workout.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: Sajal Gupta Date: Nov 11, 2025
 */
@Getter
@Setter
@Entity
@Table(name = "exercise_set")
public class ExerciseSet {

	@Id
	@GeneratedValue
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "exercise_id", nullable = false)
	private Exercise exercise;

	@Column
	private int setNumber;

	@Column
	private double weight;

	@Column
	private int reps;

	@Column
	private double duration;



}
