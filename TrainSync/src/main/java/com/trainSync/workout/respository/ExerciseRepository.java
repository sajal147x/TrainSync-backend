

package com.trainSync.workout.respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trainSync.workout.model.Exercise;

/**
 * Author: Sajal Gupta
 * Date: Nov 13, 2025
 */
public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {

	// Find all exercises performed by a user for a specific exerciseLibraryId
	@Query("""
			    SELECT e
			    FROM Exercise e
			    WHERE e.workout.userId = :userId
			      AND e.exerciseLibraryId = :exerciseLibraryId
			""")
	List<Exercise> findByUserAndExerciseLibraryId(UUID userId, UUID exerciseLibraryId);
}