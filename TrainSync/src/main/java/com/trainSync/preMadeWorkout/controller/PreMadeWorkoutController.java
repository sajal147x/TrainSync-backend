
package com.trainSync.preMadeWorkout.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.preMadeWorkout.dto.PreMadeWorkoutDto;
import com.trainSync.preMadeWorkout.dto.PreMadeWorkoutExerciseFetchDto;
import com.trainSync.preMadeWorkout.dto.PreMadeWorkoutFetchDto;
import com.trainSync.preMadeWorkout.model.PreMadeWorkout;
import com.trainSync.preMadeWorkout.model.PreMadeWorkoutExercise;
import com.trainSync.preMadeWorkout.service.PreMadeWorkoutService;
import com.trainSync.service.JwtService;

/**
 * Author: Sajal Gupta Date: Nov 18, 2025
 */
@RestController
@RequestMapping("/api")
public class PreMadeWorkoutController {

	@Autowired
	JwtService jwtService;

	@Autowired
	PreMadeWorkoutService preMadeWorkoutService;

	@PostMapping("/createPreMadeRoutine")
	public ResponseEntity<String> createWorkout(@RequestHeader("Authorization") String authHeader,
			@RequestBody PreMadeWorkoutDto dto) {
		try {
			String token = authHeader.replace("Bearer ", "");
			String userIdStr = jwtService.extractUserId(token); // validate JWT and extract Supabase UUID
			UUID userId = UUID.fromString(userIdStr);
			String result = preMadeWorkoutService.createPreMadeWorkout(dto, userId);
			System.out.println(result);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Failed to create workout");
		}
	}

	@GetMapping("/getPreMadeWorkout")
	public ResponseEntity<?> getPreMadeWorkout(@RequestHeader("Authorization") String authHeader,
			@RequestParam String preMadeWorkoutId) {
		try {
			System.out.println("CALLED" + preMadeWorkoutId);
			PreMadeWorkout workout = preMadeWorkoutService.fetchWorkout(UUID.fromString(preMadeWorkoutId));
			List<PreMadeWorkoutExercise> exercises = preMadeWorkoutService
					.fetchExercises(UUID.fromString(preMadeWorkoutId));
			PreMadeWorkoutFetchDto dto = new PreMadeWorkoutFetchDto();
			dto.setId(workout.getId().toString());
			dto.setName(workout.getName());
			for (var exercise : exercises) {
				PreMadeWorkoutExerciseFetchDto exerciseDto = new PreMadeWorkoutExerciseFetchDto();
				exerciseDto.setId(exercise.getId().toString());
				exerciseDto.setName(exercise.getExercise().getName());
				dto.getExercises().add(exerciseDto);

			}

			return ResponseEntity.ok(dto);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Failed to create workout");
		}
	}
	
	
	@GetMapping("/getPreMadeWorkouts")
	public ResponseEntity<?> getPreMadeWorkouts(@RequestHeader("Authorization") String authHeader) {
		try {
			String token = authHeader.replace("Bearer ", "");
			String userIdStr = jwtService.extractUserId(token); // validate JWT and extract Supabase UUID
			UUID userId = UUID.fromString(userIdStr);
			List<PreMadeWorkoutFetchDto> dtos = preMadeWorkoutService.findPreMadeWorkoutsForUser(userId);
			return ResponseEntity.ok(dtos);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Failed to create workout");
		}
	}

}
