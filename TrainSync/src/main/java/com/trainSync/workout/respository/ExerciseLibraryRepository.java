
package com.trainSync.workout.respository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainSync.workout.model.ExerciseLibrary;

/**
 * Author: Sajal Gupta Date: Nov 12, 2025
 */
@Repository
public interface ExerciseLibraryRepository extends JpaRepository<ExerciseLibrary, UUID> {

    
    @EntityGraph(attributePaths = { "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findByNameContainingIgnoreCase(String searchText, Pageable pageable);

    @EntityGraph(attributePaths = { "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findDistinctByTagLinks_MuscleTag_Id(UUID muscleTagId, Pageable pageable);

    @EntityGraph(attributePaths = {  "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findDistinctByEquipmentTags_Id(UUID equipmentTagId, Pageable pageable);

    @EntityGraph(attributePaths = {  "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findDistinctByNameContainingIgnoreCaseAndTagLinks_MuscleTag_Id(
            String searchText,
            UUID muscleTagId,
            Pageable pageable
    );

    @EntityGraph(attributePaths = { "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findDistinctByNameContainingIgnoreCaseAndEquipmentTags_Id(
            String searchText,
            UUID equipmentTagId,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findDistinctByTagLinks_MuscleTag_IdAndEquipmentTags_Id(
            UUID muscleTagId,
            UUID equipmentTagId,
            Pageable pageable
    );

    @EntityGraph(attributePaths = { "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findDistinctByNameContainingIgnoreCaseAndTagLinks_MuscleTag_IdAndEquipmentTags_Id(
            String searchText,
            UUID muscleTagId,
            UUID equipmentTagId,
            Pageable pageable
    );

	/**
	 * @param name
	 * @return
	 */
	ExerciseLibrary findByNameIgnoreCase(String name);

}

