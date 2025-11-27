
package com.trainSync.community.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.trainSync.user.model.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Nov 27, 2025 2:25:32 PM
 */
@Getter
@Setter
@Entity
@Builder	
@NoArgsConstructor
@AllArgsConstructor	
public class FriendLink {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserDetails userDetails;
	
	@ManyToOne
	@JoinColumn(name = "friend_id", nullable = false)
	private UserDetails friendDetails;
	
	private OffsetDateTime linkedAt;
	

}
