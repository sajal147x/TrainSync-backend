
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

    // Pagination with optional search text (name)
    Page<ExerciseLibrary> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Pagination with optional muscle tag (assuming join table)
    Page<ExerciseLibrary> findDistinctByMuscleTags_NameIn(List<String> tags, Pageable pageable);

    // Pagination with both optional filters
    Page<ExerciseLibrary> findDistinctByNameContainingIgnoreCaseAndMuscleTags_NameIn(String name, List<String> tags, Pageable pageable);

    Page<ExerciseLibrary> findByNameContaining(String searchText, Pageable pageable);
    Page<ExerciseLibrary> findByMuscleTags_Name(String muscleTag, Pageable pageable);
    Page<ExerciseLibrary> findByNameContainingAndMuscleTags_Name(String searchText, String muscleTag, Pageable pageable);

}