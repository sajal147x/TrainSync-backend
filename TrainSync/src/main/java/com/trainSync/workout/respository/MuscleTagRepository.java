

package com.trainSync.workout.respository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainSync.workout.model.MuscleTag;

/**
 * Author: Sajal Gupta
 * Date: Nov 12, 2025
 */
public interface MuscleTagRepository extends JpaRepository<MuscleTag, UUID> {
	
	Optional<MuscleTag> findByName(String name);

}