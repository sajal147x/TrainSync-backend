
package com.trainSync.workout.respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainSync.workout.model.ExerciseLibraryTagKey;
import com.trainSync.workout.model.ExerciseLibraryTagLink;

/**
 * Author: Sajal Gupta Date: Nov 14, 2025
 */
public interface ExerciseLibraryTagLinkRepository
		extends JpaRepository<ExerciseLibraryTagLink, ExerciseLibraryTagKey> {

	List<ExerciseLibraryTagLink> findByExerciseLibraryId(UUID exerciseLibraryId);

	List<ExerciseLibraryTagLink> findByMuscleTagId(UUID tagId);

	List<ExerciseLibraryTagLink> findByLevel(String level);

}