
package com.trainSync.workout.model;

import java.time.LocalDateTime;
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

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserDetails user; // assumes you already have a UserDetails entity

	@Column
	private String name; 

	@Column
	private LocalDateTime startTime;

	@Column
	private LocalDateTime endTime;

	@OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Exercise> exercises;

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
	 * @return the user
	 */
	public UserDetails getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserDetails user) {
		this.user = user;
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
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(LocalDateTime endTime) {
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

}
