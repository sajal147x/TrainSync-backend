package com.trainSync.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Author: Sajal Gupta Date: Nov 7, 2025
 */

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_details")
public class UserDetails {

	@Id
	@GeneratedValue
	private UUID id;

	@Column(nullable = true, length = 100)
	private String name;

	@Column
	private Integer age;

	@Column(nullable = true, unique = true, length = 150)
	private String email;
	
	@Column
	private String username;
	
	@Column
	private String passwordHash;
	
	@Column
	private String profilePictureUrl;
	
	@Column
	private String userType;



	public UserDetails(UUID id, String name, Integer age, String email) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.email = email;
	}


}