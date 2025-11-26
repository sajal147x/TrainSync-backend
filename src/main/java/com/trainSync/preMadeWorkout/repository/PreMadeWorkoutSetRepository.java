package com.trainSync.preMadeWorkout.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainSync.preMadeWorkout.model.PreMadeWorkoutSet;

/**
 * Author: Sajal Gupta
 * Date: Nov 18, 2025
 */

@Repository
public interface PreMadeWorkoutSetRepository  extends JpaRepository<PreMadeWorkoutSet, UUID> {

	/**
	 * @param fromString
	 * @return
	 */
	List<PreMadeWorkoutSet> findByPreMadeWorkoutExerciseId(UUID fromString);

}
