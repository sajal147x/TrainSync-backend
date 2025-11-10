package com.trainSync.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.util.UUID;

/**
 * Author: Sajal Gupta Date: Nov 7, 2025
 */

@Entity
@Table(name = "user_details")
public class UserDetails {

	@Id
	private UUID id; // matches Supabase Auth ID

	@Column(nullable = true, length = 100)
	private String name;

	@Column
	private Integer age;

	@Column(nullable = false, unique = true, length = 150)
	private String email;
	
	@Column
	private String profilePictureUrl;

	// Constructors
	public UserDetails() {
	}

	public UserDetails(UUID id, String name, Integer age, String email) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.email = email;
	}

	// Getters & Setters
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the profilePictureUrl
	 */
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	/**
	 * @param profilePictureUrl the profilePictureUrl to set
	 */
	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}
}