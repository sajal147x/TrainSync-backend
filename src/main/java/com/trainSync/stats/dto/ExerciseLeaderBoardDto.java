
package com.trainSync.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Jan 26, 2026 7:15:32 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseLeaderBoardDto {
	
	private String userId;
	private String name;
	private String profilePictureUrl;
	private Double statValue;
	

}
