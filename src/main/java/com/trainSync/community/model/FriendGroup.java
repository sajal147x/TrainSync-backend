
package com.trainSync.community.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.trainSync.user.model.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Jan 13, 2026 11:24:08 AM
 * @Description: Table for storing unique friend groups
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "friend_group")
public class FriendGroup {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@Column
	private String groupName;
	
	@ManyToOne
	@JoinColumn(name = "created_by_user_id", nullable = false)
	private UserDetails createdByUser;
	
	@Column
	private String profilePictureUrl;
	
	@Column
	private OffsetDateTime createdAt;
	

}
