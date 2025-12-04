
package com.trainSync.workout.factory;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.trainSync.preMadeWorkout.model.PreMadeWorkout;
import com.trainSync.workout.WorkoutSource;
import com.trainSync.workout.model.Workout;

/**
 * Author: Sajal Gupta
 * Created on: Dec 4, 2025 2:18:34 PM
 * Generic Factory pattern for creating workouts
 */
public class WorkoutFactory {

	public Workout createWorkout(WorkoutSource source, String name, UUID userId, OffsetDateTime workoutDate, PreMadeWorkout preMadeWorkout, UUID existingWorkoutId) {
		switch (source) {
		case NEW_BLANK:
				return createNewBlankWorkout(name,  userId,  workoutDate);
		case FROM_PRE_MADE:
			return createWorkoutFromPreMade(name,  userId,  workoutDate, preMadeWorkout);
			
		case FROM_EXISTING_WORKOUT:
			return createFromExistingWorkout(name,  userId,  workoutDate, existingWorkoutId);
		
		default:
			return null;
			
		}
	}

	/**
	 * 
	 * @param workoutDto
	 * @return
	 */
	private Workout createNewBlankWorkout(String name, UUID userId, OffsetDateTime workoutDate) {
		return Workout.builder().
				name(name)
				.startTime(workoutDate)
				.userId(userId)
				.build();

	}

	/**
	 * 
	 * @param workoutDto
	 * @return
	 */
	private Workout createWorkoutFromPreMade(String name, UUID userId, OffsetDateTime workoutDate, PreMadeWorkout preMadeWorkout) {

		return Workout.builder().
				name(name)
				.startTime(workoutDate)
				.userId(userId)
				.preMadeWorkout(preMadeWorkout)
				.build();

	
	}
	
	/**
	 * 
	 * @param workoutDto
	 * @return
	 */
	private Workout createFromExistingWorkout(String name, UUID userId, OffsetDateTime workoutDate, UUID existingWorkoutId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
