

package com.trainSync.workout.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.workout.dto.ChangeExerciseDto;
import com.trainSync.workout.dto.DeleteExerciseInWorkoutRequest;
import com.trainSync.workout.dto.DeleteSetDto;
import com.trainSync.workout.dto.EditExerciseDto;
import com.trainSync.workout.model.Exercise;
import com.trainSync.workout.model.ExerciseLibrary;
import com.trainSync.workout.respository.ExerciseLibraryRepository;
import com.trainSync.workout.respository.ExerciseRepository;
import com.trainSync.workout.respository.ExerciseSetRepository;
import com.trainSync.workout.service.EditExerciseService;

/**
 * Author: Sajal Gupta
 * Date: Nov 13, 2025
 */
@RestController
@RequestMapping("/api")
public class EditExerciseController {

    private final EditExerciseService editExerciseService;
    
    private final ExerciseSetRepository exerciseSetRepository;
    
    private final ExerciseRepository exerciseRepository;
    
    private final ExerciseLibraryRepository exerciseLibraryRepository;

    public EditExerciseController(EditExerciseService editExerciseService, ExerciseSetRepository exerciseSetRepository, ExerciseRepository exerciseRepository, ExerciseLibraryRepository exerciseLibraryRepository) {
        this.editExerciseService = editExerciseService;
        this.exerciseSetRepository = exerciseSetRepository;
        this.exerciseRepository = exerciseRepository;
        this.exerciseLibraryRepository = exerciseLibraryRepository;
    }

    // Add a set to an exercise
    @PostMapping("/add-set-to-exercise")
    public ResponseEntity<?> addSet(@RequestBody EditExerciseDto dto) {
        try {
            String setId = editExerciseService.addSetToExercise(dto);
            return ResponseEntity.ok(setId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to add set");
        }
    }

    // Edit an existing set
    @PostMapping("/update-set-in-exercise")
    public ResponseEntity<?> editSet(@RequestBody EditExerciseDto dto) {
        try {
            editExerciseService.editExerciseSet(dto);
            return ResponseEntity.ok(dto.getSetId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to update set");
        }
    }
    
	// delete an existing set
	@PostMapping("/delete-set")
	public ResponseEntity<?> deleteSet(@RequestBody DeleteSetDto dto) {
		try {
			exerciseSetRepository.deleteById(UUID.fromString(dto.getDeletedSetId()));
			for (var set : dto.getNewSets()) {
				exerciseSetRepository.updateSetNumber(UUID.fromString(set.getId()), set.getSetNumber());
			}
			return ResponseEntity.ok(200);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Failed to update set");
		}
	}
	
	/**
	 * 
	 * @param dto
	 * @return
	 */
	@PostMapping("/change-exercise-in-workout")
	public ResponseEntity<?> changeExerciseInWorkout(@RequestBody ChangeExerciseDto dto) {
		try {

			Exercise exercise = exerciseRepository.findById(UUID.fromString(dto.getExerciseId())).get();
			ExerciseLibrary exerciseLibrary = exerciseLibraryRepository
					.findById(UUID.fromString(dto.getNewExerciseLibraryId())).get();
			exercise.setExerciseLibrary(exerciseLibrary);
			exerciseRepository.save(exercise);

			return ResponseEntity.ok(200);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Failed to update set");
		}
	}
	
	/**
	 * 
	 * @param dto
	 * @return
	 */
	@PostMapping("/delete-exercise-in-workout")
	public ResponseEntity<Integer> deleteExerciseInWorkout(@RequestBody DeleteExerciseInWorkoutRequest request) {
			editExerciseService.deleteExerciseInWorkout(UUID.fromString(request.getExerciseId()));

			return ResponseEntity.ok(200);
		
	}
}
