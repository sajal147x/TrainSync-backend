

package com.trainSync.workout.respository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainSync.workout.model.EquipmentTag;


/**
 * Author: Sajal Gupta
 * Date: Nov 14, 2025
 */
public interface EquipmentTagRepository extends JpaRepository<EquipmentTag, UUID> {
	
	Optional<EquipmentTag> findByName(String name);

}