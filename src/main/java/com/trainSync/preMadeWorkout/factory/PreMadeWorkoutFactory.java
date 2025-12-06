
package com.trainSync.preMadeWorkout.factory;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.trainSync.preMadeWorkout.model.PreMadeWorkout;

/**
 * Author: Sajal Gupta
 * Created on: Dec 6, 2025 10:47:14 AM
 */
public class PreMadeWorkoutFactory {
	
	public PreMadeWorkout createPreMadeWorkout(String name, UUID userId) {
		return PreMadeWorkout.builder()
				.name(name)
				.userId(userId)
				.createdAt(OffsetDateTime.now()).build();
	}

}
