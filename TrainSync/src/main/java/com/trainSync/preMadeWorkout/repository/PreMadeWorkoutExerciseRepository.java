

package com.trainSync.preMadeWorkout.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainSync.preMadeWorkout.model.PreMadeWorkoutExercise;

/**
 * Author: Sajal Gupta
 * Date: Nov 18, 2025
 */
@Repository
public interface PreMadeWorkoutExerciseRepository  extends JpaRepository<PreMadeWorkoutExercise, UUID> {

	/**
	 * @param workoutId
	 * @return
	 */
	List<PreMadeWorkoutExercise> findByPreMadeWorkoutId(UUID workoutId);

}
