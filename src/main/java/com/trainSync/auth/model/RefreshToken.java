
package com.trainSync.auth.model;


import java.util.Date;

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
 * Author: Sajal Gupta
 * Created on: Dec 4, 2025 10:25:24 AM
 */
@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@Builder
public class RefreshToken {
	
	@GeneratedValue
	@Id
	private String id;
	
	@Column
	private String token;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserDetails userDetails;
	
	@Column
	private Date expiredDate;
	
	private boolean revoked;
	

}
