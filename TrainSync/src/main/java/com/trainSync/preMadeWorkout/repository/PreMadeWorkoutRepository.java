
package com.trainSync.preMadeWorkout.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainSync.preMadeWorkout.model.PreMadeWorkout;

/**
 * Author: Sajal Gupta Date: Nov 18, 2025
 */
@Repository
public interface PreMadeWorkoutRepository extends JpaRepository<PreMadeWorkout, UUID> {

	/**
	 * @param userId
	 * @return
	 */
	List<PreMadeWorkout> findByUserId(UUID userId);

}
