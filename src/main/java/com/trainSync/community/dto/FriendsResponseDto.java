
package com.trainSync.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Nov 27, 2025 2:50:01 PM
 */
@Getter
@Setter
@Builder
public class FriendsResponseDto {
	
	private String userId;
	private String name;
	private String profilePictureUrl;

}
