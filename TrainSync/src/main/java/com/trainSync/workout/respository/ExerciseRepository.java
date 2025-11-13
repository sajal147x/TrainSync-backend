

package com.trainSync.workout.respository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainSync.workout.model.Exercise;

/**
 * Author: Sajal Gupta
 * Date: Nov 13, 2025
 */
public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
}