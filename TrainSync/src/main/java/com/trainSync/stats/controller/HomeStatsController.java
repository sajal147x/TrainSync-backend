package com.trainSync.stats.controller;


import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.service.JwtService;
import com.trainSync.stats.dto.MonthlyExerciseCountPerMuscleDto;
import com.trainSync.workout.respository.ExerciseRepository;
import com.trainSync.workout.respository.WorkoutRepository;

/**
 * Author: Sajal Gupta
 * Date: Nov 13, 2025
 */
@RestController
@RequestMapping("/api/homeStats")
public class HomeStatsController {

	private final WorkoutRepository workoutRepository;
	
	private final JwtService jwtService;
	
	private final ExerciseRepository exerciseRepository;
	
	public HomeStatsController(WorkoutRepository workoutRepository, JwtService jwtService,
			ExerciseRepository exerciseRepository) {
		this.workoutRepository = workoutRepository;
		this.jwtService = jwtService;
		this.exerciseRepository = exerciseRepository;
	}



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
     * @return
     */
    @GetMapping("/monthlyExerciseCountPerMuscle")
	public ResponseEntity<?> getMonthlyExerciseCountPerMuscle(@RequestHeader("Authorization") String authHeader) {
		// Extract user ID from JWT token
		String token = authHeader.replace("Bearer ", "");
		String userIdStr = jwtService.extractUserId(token);
		UUID userId = UUID.fromString(userIdStr);
		List<MonthlyExerciseCountPerMuscleDto> dto = exerciseRepository.findPrimaryMuscleCounts(userId,
				OffsetDateTime.now().minusDays(30));

		return ResponseEntity.ok(dto);
	}
    
    public static void main(String[] args) {
    	
    	//List<Map<String, Object>> map = exerciseRepository.findPrimaryMuscleCounts(UUID.fromString(""));
    	
		
	}
    
    
}
