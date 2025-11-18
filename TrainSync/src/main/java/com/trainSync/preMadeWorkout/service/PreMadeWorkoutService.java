
package com.trainSync.preMadeWorkout.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainSync.preMadeWorkout.dto.PreMadeWorkoutDto;
import com.trainSync.preMadeWorkout.dto.PreMadeWorkoutFetchDto;
import com.trainSync.preMadeWorkout.model.PreMadeWorkout;
import com.trainSync.preMadeWorkout.model.PreMadeWorkoutExercise;
import com.trainSync.preMadeWorkout.repository.PreMadeWorkoutExerciseRepository;
import com.trainSync.preMadeWorkout.repository.PreMadeWorkoutRepository;
import com.trainSync.workout.model.ExerciseLibrary;
import com.trainSync.workout.respository.EquipmentTagRepository;
import com.trainSync.workout.respository.ExerciseLibraryRepository;

/**
 * Author: Sajal Gupta Date: Nov 18, 2025
 */
@Service
public class PreMadeWorkoutService {

	@Autowired
	PreMadeWorkoutRepository preMadeWorkoutRepository;

	@Autowired
	ExerciseLibraryRepository exerciseLibraryRepository;

	@Autowired
	PreMadeWorkoutExerciseRepository preMadeWorkoutExerciseRepository;

	@Autowired
	EquipmentTagRepository equipmentTagRepository;

	/**
	 * @param dto
	 * @param userId
	 * @return
	 */
	public String createPreMadeWorkout(PreMadeWorkoutDto dto, UUID userId) {
		// Create and save workout
		PreMadeWorkout workout = new PreMadeWorkout();
		workout.setName(dto.getName());
		workout.setUserId(userId);
		workout.setCreatedAt(OffsetDateTime.now());
		preMadeWorkoutRepository.save(workout);

		// Create and save exercise linked to workout
		PreMadeWorkoutExercise exercise = new PreMadeWorkoutExercise();
		exercise.setPreMadeWorkout(workout);
		ExerciseLibrary exerciseLib = exerciseLibraryRepository.findById(UUID.fromString(dto.getExerciseId())).get();
		exercise.setExercise(exerciseLib);
		exercise.setEquipment(equipmentTagRepository.findById(UUID.fromString(dto.getEquipmentId())).get());

		preMadeWorkoutExerciseRepository.save(exercise);

		return workout.getId().toString();

	}

	/**
	 * 
	 * @param workoutId
	 * @return
	 */
	public PreMadeWorkout fetchWorkout(UUID workoutId) {
		return preMadeWorkoutRepository.findById(workoutId).get();
	}

	/**
	 * @param fromString
	 * @return
	 */
	public List<PreMadeWorkoutExercise> fetchExercises(UUID workoutId) {
		return preMadeWorkoutExerciseRepository.findByPreMadeWorkoutId(workoutId);
		
	}

	/**
	 * @param userId
	 * @return
	 */
	public List<PreMadeWorkoutFetchDto> findPreMadeWorkoutsForUser(UUID userId) {
		List<PreMadeWorkout> workouts = preMadeWorkoutRepository.findByUserId(userId);
		List<PreMadeWorkoutFetchDto> dtos = new ArrayList<PreMadeWorkoutFetchDto>();
		for(var workout : workouts) {
			PreMadeWorkoutFetchDto dto = new PreMadeWorkoutFetchDto();
			dto.setId(workout.getId().toString());
			dto.setName(workout.getName());
			dtos.add(dto);
		}
		return dtos;
	}

}
