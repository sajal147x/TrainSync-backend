
package com.trainSync.workout.respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.trainSync.workout.model.ExerciseLibrary;

/**
 * Author: Sajal Gupta Date: Nov 12, 2025
 */
@Repository
public interface ExerciseLibraryRepository extends JpaRepository<ExerciseLibrary, UUID> {


    // Pagination with optional muscle tag (assuming join table)
    Page<ExerciseLibrary> findDistinctByMuscleTags_NameIn(List<String> tags, Pageable pageable);

    // Pagination with both optional filters
    Page<ExerciseLibrary> findDistinctByNameContainingIgnoreCaseAndMuscleTags_NameIn(String name, List<String> tags, Pageable pageable);

    Page<ExerciseLibrary> findByNameContainingIgnoreCase(String searchText, Pageable pageable);
    Page<ExerciseLibrary> findByMuscleTags_Id(UUID muscleTagId, Pageable pageable);
    Page<ExerciseLibrary> findByNameContainingIgnoreCaseAndMuscleTags_Id(String searchText, UUID muscleTag, Pageable pageable);

	/**
	 * @param searchText
	 * @param muscleTagUuid
	 * @param equipmentTagUuid
	 * @param pageable
	 * @return
	 */
	Page<ExerciseLibrary> findByNameContainingIgnoreCaseAndMuscleTags_IdAndEquipmentTags_Id(String searchText,
			UUID muscleTagUuid, UUID equipmentTagUuid, Pageable pageable);

	/**
	 * @param searchText
	 * @param equipmentTagUuid
	 * @param pageable
	 * @return
	 */
	Page<ExerciseLibrary> findByNameContainingIgnoreCaseAndEquipmentTags_Id(String searchText, UUID equipmentTagUuid,
			Pageable pageable);

	/**
	 * @param muscleTagUuid
	 * @param equipmentTagUuid
	 * @param pageable
	 * @return
	 */
	Page<ExerciseLibrary> findByMuscleTags_IdAndEquipmentTags_Id(UUID muscleTagUuid, UUID equipmentTagUuid,
			Pageable pageable);

	/**
	 * @param equipmentTagUuid
	 * @param pageable
	 * @return
	 */
	Page<ExerciseLibrary> findByEquipmentTags_Id(UUID equipmentTagUuid, Pageable pageable);

}