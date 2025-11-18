
package com.trainSync.preMadeWorkout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.preMadeWorkout.service.EditPreMadeExerciseService;
import com.trainSync.workout.dto.EditExerciseDto;

/**
 * Author: Sajal Gupta
 * Date: Nov 18, 2025
 */
@RestController
@RequestMapping("/api")
public class EditPreMadeWorkoutExerciseController {
	
	@Autowired
	EditPreMadeExerciseService editPreMadeExerciseService;
	
	// Add a set to an exercise
    @PostMapping("/add-set-to-pre-made-workout")
    public ResponseEntity<?> addSet(@RequestBody EditExerciseDto dto) {
        try {
            String setId = editPreMadeExerciseService.addSetToExercise(dto.getExerciseId(), dto.getSetNumber());
            return ResponseEntity.ok(setId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to add set");
        }
    }

}
