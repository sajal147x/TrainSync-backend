
package com.trainSync.notification.model;

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
 * Created on: Jan 19, 2026 4:01:05 PM
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "push_notification_token")
public class PushNotificationToken {
	
	@GeneratedValue
	@Id
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserDetails user;
	
	@Column
	private String token;
	
	@Column
	private String platform;
	
	@Column
	private String status;
	

}
