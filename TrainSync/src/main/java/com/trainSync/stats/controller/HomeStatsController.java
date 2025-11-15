package com.trainSync.stats.controller;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.service.JwtService;
import com.trainSync.stats.dto.ExerciseProgressionDto;
import com.trainSync.stats.service.ExerciseProgressionService;
import com.trainSync.workout.respository.WorkoutRepository;

/**
 * Author: Sajal Gupta
 * Date: Nov 13, 2025
 */
@RestController
@RequestMapping("/api/homeStats")
public class HomeStatsController {

	@Autowired
    private WorkoutRepository workoutRepository;
	
	@Autowired
    private JwtService jwtService;
	
	@Autowired
	private ExerciseProgressionService exerciseProgressionService;


    @GetMapping("/loggedWorkouts")
    public ResponseEntity<?> getLoggedWorkouts(@RequestHeader("Authorization") String authHeader) {
        try {
            // Extract user ID from JWT token
            String token = authHeader.replace("Bearer ", "");
            String userIdStr = jwtService.extractUserId(token);
            UUID userId = UUID.fromString(userIdStr);

            // Query workout count
            long count = workoutRepository.countByUserId(userId);

            return ResponseEntity.ok(count);

        } catch (IllegalArgumentException e) {
        	e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid UUID in JWT");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to fetch logged workouts");
        }
    }
    
    
    /**
     * 
     * @param authHeader
     * @param exerciseId
     * @return
     */
    @GetMapping("/exerciseProgression")
    public ResponseEntity<List<ExerciseProgressionDto>> getExerciseProgression(
            @RequestHeader("Authorization") String authHeader,
			@RequestParam("exerciseId") String exerciseId) {

		// Extract userId from JWT
		String token = authHeader.replace("Bearer ", "");
		String userId = jwtService.extractUserId(token);

		List<ExerciseProgressionDto> progression = exerciseProgressionService
				.computeExerciseTonnageProgression(UUID.fromString(userId), UUID.fromString(exerciseId));

		return ResponseEntity.ok(progression);
	}
    
    
}
