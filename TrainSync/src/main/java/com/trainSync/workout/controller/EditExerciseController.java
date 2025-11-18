

package com.trainSync.workout.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.workout.dto.DeleteSetDto;
import com.trainSync.workout.dto.EditExerciseDto;
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
    
    @Autowired
    private ExerciseSetRepository exerciseSetRepository;

    public EditExerciseController(EditExerciseService editExerciseService) {
        this.editExerciseService = editExerciseService;
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
}
