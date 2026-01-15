
package com.trainSync.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Jan 15, 2026 3:30:29 PM
 */
@Getter
@Setter
@Builder	
public class GroupMemberDto {
	
	private String userId;
	private String name;
	private String profilePictureUrl;

}
