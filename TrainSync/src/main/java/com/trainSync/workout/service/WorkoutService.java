package com.trainSync.workout.service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainSync.workout.dto.WorkoutDto;
import com.trainSync.workout.model.Exercise;
import com.trainSync.workout.model.Workout;
import com.trainSync.workout.respository.ExerciseRepository;
import com.trainSync.workout.respository.WorkoutRepository;

/**
 * Author: Sajal Gupta
 * Date: Nov 13, 2025
 */
@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    public String createWorkout(WorkoutDto workoutDto, UUID userId) {
    	// Convert date string to LocalDateTime
    	OffsetDateTime dateTime = OffsetDateTime.parse(workoutDto.getWorkoutDate(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        // Create and save workout
        Workout workout = new Workout();
        workout.setName(workoutDto.getWorkoutName());
        workout.setStartTime(dateTime);
        workout.setUserId(userId);
        workoutRepository.save(workout);

        // Create and save exercise linked to workout
        Exercise exercise = new Exercise();
        exercise.setWorkout(workout);
        exercise.setExerciseLibraryId(UUID.fromString(workoutDto.getExerciseId()));
        exercise.setName(workoutDto.getWorkoutName());
        exerciseRepository.save(exercise);

        return workout.getId().toString();
    }
}
