package com.trainSync.workout.respository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainSync.workout.model.ExerciseLibrary;

@Repository
public interface ExerciseLibraryRepository extends JpaRepository<ExerciseLibrary, UUID> {

    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findByNameContainingIgnoreCase(String searchText, Pageable pageable);

    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findByEquipment_Id(UUID equipmentId, Pageable pageable);

    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findByTagLinks_MuscleTag_Id(UUID muscleTagId, Pageable pageable);

    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findByNameContainingIgnoreCaseAndTagLinks_MuscleTag_Id(
            String searchText,
            UUID muscleTagId,
            Pageable pageable
    );

    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findByNameContainingIgnoreCaseAndEquipment_Id(
            String searchText,
            UUID equipmentId,
            Pageable pageable
    );

    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findByTagLinks_MuscleTag_IdAndEquipment_Id(
            UUID muscleTagId,
            UUID equipmentId,
            Pageable pageable
    );

    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findByNameContainingIgnoreCaseAndTagLinks_MuscleTag_IdAndEquipment_Id(
            String searchText,
            UUID muscleTagId,
            UUID equipmentId,
            Pageable pageable
    );

    ExerciseLibrary findByNameIgnoreCase(String name);
}
