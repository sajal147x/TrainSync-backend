
package com.trainSync.workout.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.trainSync.user.model.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Author: Sajal Gupta Date: Nov 11, 2025
 */

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
	 * @return the startTime
	 */
	public OffsetDateTime getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(OffsetDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public OffsetDateTime getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(OffsetDateTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the exercises
	 */
	public List<Exercise> getExercises() {
		return exercises;
	}

	/**
	 * @param exercises the exercises to set
	 */
	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

	/**
	 * @return the userId
	 */
	public UUID getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(UUID userId) {
		this.userId = userId;
	}

}
