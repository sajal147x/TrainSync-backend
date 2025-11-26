
package com.trainSync.workout.respository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trainSync.workout.model.ExerciseSet;

import jakarta.transaction.Transactional;

/**
 * Author: Sajal Gupta Date: Nov 13, 2025
 */
public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, UUID> {

	@Modifying
	@Transactional
	@Query("UPDATE ExerciseSet s SET s.setNumber = :setNumber WHERE s.id = :id")
	int updateSetNumber(@Param("id") UUID id, @Param("setNumber") int setNumber);
}