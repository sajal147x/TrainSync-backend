/**
 * Author: Sajal Gupta
 * Date: Nov 14, 2025
 */

package com.trainSync.workout.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainSync.workout.model.ExerciseLibraryEquipmentKey;
import com.trainSync.workout.model.ExerciseLibraryEquipmentLink;

public interface ExerciseLibraryEquipmentLinkRepository
		extends JpaRepository<ExerciseLibraryEquipmentLink, ExerciseLibraryEquipmentKey> {
}