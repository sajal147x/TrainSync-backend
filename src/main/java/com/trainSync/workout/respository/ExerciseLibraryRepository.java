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

    // Search only by name
    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findByNameContainingIgnoreCase(
            String searchText,
            Pageable pageable
    );

    // Equipment only
    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findByEquipment_Id(
            UUID equipmentId,
            Pageable pageable
    );

    // MUSCLE TAG (PRIMARY ONLY)
    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findByTagLinks_MuscleTag_IdAndTagLinks_Level(
            UUID muscleTagId,
            String level, // always pass "PRIMARY"
            Pageable pageable
    );

    // SEARCH + MUSCLE TAG (PRIMARY ONLY)
    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findByNameContainingIgnoreCaseAndTagLinks_MuscleTag_IdAndTagLinks_Level(
            String searchText,
            UUID muscleTagId,
            String level, // always pass "PRIMARY"
            Pageable pageable
    );

    // SEARCH + EQUIPMENT
    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findByNameContainingIgnoreCaseAndEquipment_Id(
            String searchText,
            UUID equipmentId,
            Pageable pageable
    );

    // EQUIPMENT + MUSCLE TAG (PRIMARY ONLY)
    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findByTagLinks_MuscleTag_IdAndTagLinks_LevelAndEquipment_Id(
            UUID muscleTagId,
            String level, // always "PRIMARY"
            UUID equipmentId,
            Pageable pageable
    );

    // SEARCH + MUSCLE TAG (PRIMARY ONLY) + EQUIPMENT
    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    Page<ExerciseLibrary> findByNameContainingIgnoreCaseAndTagLinks_MuscleTag_IdAndTagLinks_LevelAndEquipment_Id(
            String searchText,
            UUID muscleTagId,
            String level, // always "PRIMARY"
            UUID equipmentId,
            Pageable pageable
    );

    ExerciseLibrary findByNameIgnoreCase(String name);
    
    Page<ExerciseLibrary> findByExercisePictureUrlIsNull(Pageable pageable);
   
}
