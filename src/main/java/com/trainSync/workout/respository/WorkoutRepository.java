

package com.trainSync.workout.respository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trainSync.workout.dto.UserWorkoutCount;
import com.trainSync.workout.model.Workout;

/**
 * Author: Sajal Gupta
 * Date: Nov 13, 2025
 */
public interface WorkoutRepository extends JpaRepository<Workout, UUID> {

	List<Workout> findTop5ByUserIdOrderByStartTimeDesc(UUID userId);

	List<Workout> findTop10ByUserIdOrderByStartTimeDesc(UUID userId);

	/**
	 * @param userId
	 * @return
	 */
	long countByUserId(UUID userId);
	
	@Query("""
		    SELECT w.userId AS userId, COUNT(w) AS workoutCount
		    FROM Workout w
		    WHERE w.userId IN :userIds
		      AND w.startTime >= :since
		    GROUP BY w.userId
		""")
		List<UserWorkoutCount> countWorkoutsPerUserSince(
		        @Param("userIds") List<UUID> userIds,
		        @Param("since") OffsetDateTime since
		);
}
