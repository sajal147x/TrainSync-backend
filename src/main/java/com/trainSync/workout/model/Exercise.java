
package com.trainSync.workout.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

/**
 * Author: Sajal Gupta Date: Nov 11, 2025
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
	
	
	@ManyToOne
	@JoinColumn(name = "exercise_id")
	private ExerciseLibrary exerciseLibrary;


	
	@Column
	private String preFilledFromLastWorkoutFlag = "NO";
	
	@ManyToOne
	@JoinColumn(name = "pre_filled_workout_id", nullable = true)
	private Workout preFilledWorkout;
	
	@Column
	private int exerciseOrder;
	
	@Column
	@Builder.Default
	private String editedFlag = "NO";


}
