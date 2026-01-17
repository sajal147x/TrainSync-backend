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
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sajalgupta
 * @dateCreated: Jan 17, 2026
 */
@Entity
@Table(name="group_message")
@Getter
@Setter
@Builder
public class GroupMessage {
	@Id
	@GeneratedValue
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name="group_id")
	private FriendGroup group;

	@ManyToOne
	@JoinColumn(name="sender_user_id")
	private UserDetails sender;
	
	@Column
	private String message;
	
	@Column
	private OffsetDateTime sentAt;

}
