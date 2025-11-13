

package com.trainSync.workout.respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainSync.workout.model.Workout;

/**
 * Author: Sajal Gupta
 * Date: Nov 13, 2025
 */
public interface WorkoutRepository extends JpaRepository<Workout, UUID> {

	List<Workout> findTop5ByUserIdOrderByStartTimeDesc(UUID userId);

	/**
	 * @param userId
	 * @return
	 */
	long countByUserId(UUID userId);
}