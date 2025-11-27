
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
 * Created on: Nov 27, 2025 12:34:40 PM
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRequest {
	@Id
	@GeneratedValue
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "sender_id", nullable = false)
	private UserDetails senderDetails;
	
	@ManyToOne
	@JoinColumn(name = "receiver_id", nullable = false)
	private UserDetails receiverDetails;
	
	private String status; // PENDING, ACCEPTED, REJECTED
	
	private OffsetDateTime sentAt;
	
	public static final String STATUS_PENDING = "PENDING";
	public static final String STATUS_ACCEPTED = "ACCEPTED";
	public static final String STATUS_REJECTED = "REJECTED";
	public static final String STATUS_NONE = "NONE";

}
