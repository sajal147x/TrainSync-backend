
package com.trainSync.community.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.BatchSize;

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
 * Created on: Jan 13, 2026 11:33:42 AM
 * @Description: Table Storing group - member link
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "friend_group_member_link")
public class FriendGroupMemberLink {
	
	@Id
	@GeneratedValue
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "friend_group_id", nullable = false)
	private FriendGroup friendGroup;
	
	@ManyToOne
	@JoinColumn(name = "group_member_user_id", nullable = false)
	private UserDetails groupMember;
	
	@Column
	private OffsetDateTime joinedAt;
	

}
