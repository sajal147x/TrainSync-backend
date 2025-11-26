package com.trainSync.workout.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainSync.service.JwtService;
import com.trainSync.workout.dto.RecentWorkoutDto;
import com.trainSync.workout.model.Workout;
import com.trainSync.workout.respository.WorkoutRepository;

/**
 * Author: Sajal Gupta
 * Date: Nov 13, 2025
 */
@RestController
@RequestMapping("/api")
public class RecentWorkoutsController {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private JwtService jwtService;

    /**
     * Fetch recent 5 workouts for the authenticated user
     *
     * @param authHeader JWT Authorization header
     * @return List of recent workouts
     */
    @GetMapping("/get-recent-workouts")
    public ResponseEntity<?> getRecentWorkouts(@RequestHeader("Authorization") String authHeader) {
        try {
            // Extract token and parse user ID
            String token = authHeader.replace("Bearer ", "");
            String userIdStr = jwtService.extractUserId(token);
            UUID userId = UUID.fromString(userIdStr);

            // Fetch top 5 most recent workouts
            List<Workout> recentWorkouts = workoutRepository
                    .findTop5ByUserIdOrderByStartTimeDesc(userId);

            List<RecentWorkoutDto> dtoList = recentWorkouts.stream()
                    .map(w -> new RecentWorkoutDto(w.getId().toString(), w.getName(), w.getStartTime().toString()))
                    .toList();

            return ResponseEntity.ok(dtoList);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID format in token");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to fetch recent workouts");
        }
    }
}
