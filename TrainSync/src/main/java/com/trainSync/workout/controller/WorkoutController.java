package com.trainSync.workout.controller;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.service.JwtService;
import com.trainSync.workout.dto.ExerciseDto;
import com.trainSync.workout.dto.WorkoutDto;
import com.trainSync.workout.model.Workout;
import com.trainSync.workout.respository.WorkoutRepository;
import com.trainSync.workout.service.WorkoutService;

/**
 * Author: Sajal Gupta Date: Nov 13, 2025
 */
@RestController
@RequestMapping("/api")
public class WorkoutController {

	@Autowired
	JwtService jwtService;

	@Autowired
	private WorkoutService workoutService;

	@Autowired
	private WorkoutRepository workoutRepository;

	@PostMapping("/create-workout")
	public ResponseEntity<String> createWorkout(@RequestHeader("Authorization") String authHeader,
			@RequestBody WorkoutDto workoutDto) {
		try {
			String token = authHeader.replace("Bearer ", "");
			String userIdStr = jwtService.extractUserId(token); // validate JWT and extract Supabase UUID
			UUID userId = UUID.fromString(userIdStr);
			String result = workoutService.createWorkout(workoutDto, userId);
			System.out.println(result);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Failed to create workout");
		}
	}

	/**
	 * 
	 * @param authHeader
	 * @param workoutIdStr
	 * @return
	 */
	@GetMapping("/get-workout")
	public ResponseEntity<?> retrieveWorkout(
	        @RequestHeader("Authorization") String authHeader,
	        @RequestParam String workoutId) {

	    try {
	    	System.out.println("WORKOUT ID IS " + workoutId);
	        // Extract user ID from JWT
	        String token = authHeader.replace("Bearer ", "");
	        String userIdStr = jwtService.extractUserId(token);
	        UUID userId = UUID.fromString(userIdStr);

	        // Fetch workout by ID
	        UUID workoutIdUUID = UUID.fromString(workoutId);
	        Optional<Workout> optionalWorkout = workoutRepository.findById(workoutIdUUID);

	        if (optionalWorkout.isEmpty()) {
	            return ResponseEntity.status(404).body("Workout not found");
	        }

	        Workout workout = optionalWorkout.get();

	        // Authorization check
	        if (!workout.getUserId().equals(userId)) {
	            return ResponseEntity.status(403).body("You are not authorized to view this workout");
	        }

	        // Map entity to DTO
	        WorkoutDto workoutDto = new WorkoutDto();
	        workoutDto.setExerciseId(workout.getId().toString());
	        workoutDto.setWorkoutName(workout.getName());
	        workoutDto.setExercises(
	                workout.getExercises().stream()
	                        .map(e -> new ExerciseDto(e.getName()))
	                        .collect(Collectors.toList())
	        );

	        return ResponseEntity.ok(workoutDto);

	    } catch (IllegalArgumentException e) {
	    	e.printStackTrace();
	        return ResponseEntity.badRequest().body("Invalid UUID format");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(500).body("Failed to retrieve workout");
	    }
	}
}
