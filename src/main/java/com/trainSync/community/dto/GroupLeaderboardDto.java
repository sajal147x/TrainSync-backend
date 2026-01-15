
package com.trainSync.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Jan 15, 2026 10:45:06 AM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupLeaderboardDto {
	
	private String userId;
	private String name;
	private int workoutsThisWeek;
	private String profilePictureUrl;
	

}
