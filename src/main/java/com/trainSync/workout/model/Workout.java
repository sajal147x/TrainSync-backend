
package com.trainSync.workout.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.trainSync.preMadeWorkout.model.PreMadeWorkout;

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
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "workout")
public class Workout {

	@Id
	@GeneratedValue
	private UUID id;


	@Column
	private String name; 

	@Column
	private OffsetDateTime startTime;

	@Column
	private OffsetDateTime endTime;

	@OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Exercise> exercises;
	
	@Column
	private UUID userId;
	
	@ManyToOne
	@JoinColumn(name = "pre_made_workout_id")
	private PreMadeWorkout preMadeWorkout;
	



}
