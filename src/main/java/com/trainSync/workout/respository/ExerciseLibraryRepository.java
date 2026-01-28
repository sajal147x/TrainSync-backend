package com.trainSync.workout.respository;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainSync.workout.model.ExerciseLibrary;

@Repository
public interface ExerciseLibraryRepository extends JpaRepository<ExerciseLibrary, UUID> {

    // Search only by name
    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    List<ExerciseLibrary> findByNameContainingIgnoreCase(
            String searchText
    );

    // Equipment only
    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    List<ExerciseLibrary> findByEquipment_Id(
            UUID equipmentId
    );

    // MUSCLE TAG (PRIMARY ONLY)
    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    List<ExerciseLibrary> findByTagLinks_MuscleTag_IdAndTagLinks_Level(
            UUID muscleTagId,
            String level // always pass "PRIMARY"
            
    );

    // SEARCH + MUSCLE TAG (PRIMARY ONLY)
    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    List<ExerciseLibrary> findByNameContainingIgnoreCaseAndTagLinks_MuscleTag_IdAndTagLinks_Level(
            String searchText,
            UUID muscleTagId,
            String level // always pass "PRIMARY"
          
    );

    // SEARCH + EQUIPMENT
    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    List<ExerciseLibrary> findByNameContainingIgnoreCaseAndEquipment_Id(
            String searchText,
            UUID equipmentId
            
    );

    // EQUIPMENT + MUSCLE TAG (PRIMARY ONLY)
    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    List<ExerciseLibrary> findByTagLinks_MuscleTag_IdAndTagLinks_LevelAndEquipment_Id(
            UUID muscleTagId,
            String level, // always "PRIMARY"
            UUID equipmentId
    );

    // SEARCH + MUSCLE TAG (PRIMARY ONLY) + EQUIPMENT
    @EntityGraph(attributePaths = { "equipment", "tagLinks", "tagLinks.muscleTag" })
    List<ExerciseLibrary> findByNameContainingIgnoreCaseAndTagLinks_MuscleTag_IdAndTagLinks_LevelAndEquipment_Id(
            String searchText,
            UUID muscleTagId,
            String level, // always "PRIMARY"
            UUID equipmentId
    );

    ExerciseLibrary findByNameIgnoreCase(String name);
    
    List<ExerciseLibrary> findByExercisePictureUrlIsNull();
   
}
