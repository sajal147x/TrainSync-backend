

package com.trainSync.workout.respository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trainSync.stats.dto.ExerciseStats;
import com.trainSync.stats.dto.MonthlyExerciseCountPerMuscleDto;
import com.trainSync.stats.dto.TopExerciseCount;
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
			      AND e.exerciseLibrary.id = :exerciseLibraryId
			""")
	List<Exercise> findByUserAndExerciseLibraryId(UUID userId, UUID exerciseLibraryId);
	
	// Find latest exercise for user
	@Query("""
			    SELECT e
			    FROM Exercise e
			    WHERE e.workout.userId = :userId
			      AND e.exerciseLibrary.id  = :exerciseLibraryId
			      order by e.workout.startTime desc limit 1
			""")
	Exercise findLatestExerciseForUser(UUID userId, UUID exerciseLibraryId);

	@Query("""
		    SELECT new com.trainSync.stats.dto.MonthlyExerciseCountPerMuscleDto(
		        mt.muscleGroup,
		        COUNT(e)
		    )
		    FROM Exercise e
		    JOIN e.workout w
		    JOIN e.exerciseLibrary el
		    JOIN el.tagLinks tl
		    JOIN tl.muscleTag mt
		    WHERE w.userId = :userId
		      AND w.startTime >= :cutoff
		      AND tl.level = 'PRIMARY'
		    GROUP BY mt.muscleGroup
		    ORDER BY MAX(w.startTime) DESC
		""")
	List<MonthlyExerciseCountPerMuscleDto> findPrimaryMuscleCounts(@Param("userId") UUID userId, @Param("cutoff") OffsetDateTime cutoff);
	
	
	@Query("""
		    SELECT
		        e.exerciseLibrary.id AS exerciseLibraryId,
		        e.exerciseLibrary.name AS exerciseName,
		        e.exerciseLibrary.exercisePictureUrl AS exercisePictureUrl,
		        COUNT(e) AS count
		    FROM Exercise e
		    JOIN e.workout w
		    WHERE w.userId = :userId
		      AND e.exerciseLibrary IS NOT NULL
		    GROUP BY
		        e.exerciseLibrary.id,
		        e.exerciseLibrary.name,
		        e.exerciseLibrary.exercisePictureUrl
		    ORDER BY COUNT(e) DESC
		""")
		List<TopExerciseCount> findTopExercisesForUser(
		        @Param("userId") UUID userId,
		        Pageable pageable
		);
	
	@Query("""
		    SELECT
		        COUNT(DISTINCT e.id) AS totalCount,
		        AVG(SIZE(e.sets)) AS avgSets,
		        MAX(s.weight) AS maxWeight
		    FROM Exercise e
		    JOIN e.workout w
		    JOIN e.sets s
		    WHERE w.userId = :userId
		      AND e.exerciseLibrary.id = :exerciseLibraryId
		""")
		ExerciseStats getExerciseStats(
		        UUID userId,
		        UUID exerciseLibraryId
		);
	
	@Query("""
		    SELECT MAX(s.reps)
		    FROM Exercise e
		    JOIN e.workout w
		    JOIN e.sets s
		    WHERE w.userId = :userId
		      AND e.exerciseLibrary.id = :exerciseLibraryId
		      AND s.weight = (
		          SELECT MAX(s2.weight)
		          FROM Exercise e2
		          JOIN e2.workout w2
		          JOIN e2.sets s2
		          WHERE w2.userId = :userId
		            AND e2.exerciseLibrary.id = :exerciseLibraryId
		      )
		""")
		Integer findHighestRepsAtMaxWeight(
		        @Param("userId") UUID userId,
		        @Param("exerciseLibraryId") UUID exerciseLibraryId
		);


}