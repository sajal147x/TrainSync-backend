
package com.trainSync.auth.model;


import java.util.Date;
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
 * Created on: Dec 4, 2025 10:25:24 AM
 */
@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
	
	@GeneratedValue
	@Id
	private UUID id;
	
	@Column
	private String token;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserDetails userDetails;
	
	@Column
	private Date expiredDate;
	
	private boolean revoked;
	

}
