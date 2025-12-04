
package com.trainSync.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trainSync.community.dto.FriendSummaryDto;
import com.trainSync.workout.dto.WorkoutDto;
import com.trainSync.workout.model.Workout;
import com.trainSync.workout.respository.WorkoutRepository;

/**
 * Author: Sajal Gupta
 * Created on: Dec 4, 2025 4:12:16 PM
 */

@Service
public class FriendSummaryService {
	
	private final WorkoutRepository workoutRepository;
	
	FriendSummaryService(WorkoutRepository workoutRepository) {
		this.workoutRepository = workoutRepository;
	}
	
	/**
	 * 
	 * @param friendUserIdUuid
	 * @return
	 */
	public FriendSummaryDto getFriendSummary(UUID friendUserIdUuid) {
		
		// Query workout count
        long count = workoutRepository.countByUserId(friendUserIdUuid);
        
        List<Workout> recentWorkouts = workoutRepository.findTop5ByUserIdOrderByStartTimeDesc(friendUserIdUuid);
        
        List<WorkoutDto> recentWorkoutDtos = new ArrayList<>();
        
        for(Workout workout : recentWorkouts) {
			WorkoutDto workoutDto = WorkoutDto.builder()
					.workoutId(workout.getId().toString())
					.workoutDate(workout.getStartTime().toString())
					.workoutName(workout.getName())
					.build();
			recentWorkoutDtos.add(workoutDto);
		}
        
        FriendSummaryDto friendSummaryDto = FriendSummaryDto.builder()
				.workoutCountThisWeek(count)
				.recentWorkouts(recentWorkoutDtos) 
				.build();
        
        return friendSummaryDto;
        
	}

}
