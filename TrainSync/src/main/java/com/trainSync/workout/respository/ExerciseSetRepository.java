

package com.trainSync.workout.respository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainSync.workout.model.ExerciseSet;

/**
 * Author: Sajal Gupta
 * Date: Nov 13, 2025
 */
public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, UUID> { }