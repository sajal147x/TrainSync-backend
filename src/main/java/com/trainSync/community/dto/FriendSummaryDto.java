
package com.trainSync.community.dto;

import java.util.List;

import com.trainSync.workout.dto.WorkoutDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Dec 4, 2025 4:10:54 PM
 */
@Getter
@Setter
@Builder
public class FriendSummaryDto {
	
	private long workoutCountThisWeek;
	
	private List<WorkoutDto> recentWorkouts;

}
