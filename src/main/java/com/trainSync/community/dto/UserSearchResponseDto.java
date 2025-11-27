
package com.trainSync.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Nov 27, 2025 12:40:17 PM
 */
@Getter
@Setter
@Builder
public class UserSearchResponseDto {
	private String requestId;
	private String userId;
	private String name;
	private Integer age;
	private String email;
	private String profilePictureUrl;
	private String requestStatus; // "NONE", "PENDING", "ACCEPTED"

}
