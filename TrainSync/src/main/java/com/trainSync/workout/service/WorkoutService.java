package com.trainSync.workout.service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainSync.workout.dto.WorkoutDto;
import com.trainSync.workout.model.Exercise;
import com.trainSync.workout.model.ExerciseLibrary;
import com.trainSync.workout.model.Workout;
import com.trainSync.workout.respository.ExerciseLibraryRepository;
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
    
    @Autowired
    private ExerciseLibraryRepository exerciseLibraryRepository;

	public String createWorkout(WorkoutDto workoutDto, UUID userId) {
		// Convert date string to LocalDateTime
		OffsetDateTime dateTime = OffsetDateTime.parse(workoutDto.getWorkoutDate(),
				DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		// Create and save workout
		Workout workout = new Workout();
		workout.setName(workoutDto.getWorkoutName());
		workout.setStartTime(dateTime);
		workout.setUserId(userId);
		workoutRepository.save(workout);

		// Create and save exercise linked to workout
		Exercise exercise = new Exercise();
		exercise.setWorkout(workout);
		ExerciseLibrary exerciseLib = exerciseLibraryRepository.findById(UUID.fromString(workoutDto.getExerciseId()))
				.get();
		exercise.setExerciseLibraryId(UUID.fromString(workoutDto.getExerciseId()));
		exercise.setName(exerciseLib.getName());
		exerciseRepository.save(exercise);

		return workout.getId().toString();
	}
}
