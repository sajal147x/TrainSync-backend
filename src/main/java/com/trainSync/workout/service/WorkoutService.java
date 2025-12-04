package com.trainSync.workout.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.trainSync.TrainSyncApplication;
import com.trainSync.preMadeWorkout.model.PreMadeWorkout;
import com.trainSync.preMadeWorkout.model.PreMadeWorkoutExercise;
import com.trainSync.preMadeWorkout.model.PreMadeWorkoutSet;
import com.trainSync.preMadeWorkout.repository.PreMadeWorkoutSetRepository;
import com.trainSync.workout.dto.WorkoutDto;
import com.trainSync.workout.factory.ExerciseFactory;
import com.trainSync.workout.factory.ExerciseSetFactory;
import com.trainSync.workout.factory.WorkoutFactory;
import com.trainSync.workout.model.Exercise;
import com.trainSync.workout.model.ExerciseLibrary;
import com.trainSync.workout.model.ExerciseSet;
import com.trainSync.workout.model.Workout;
import com.trainSync.workout.respository.ExerciseLibraryRepository;
import com.trainSync.workout.respository.ExerciseRepository;
import com.trainSync.workout.respository.ExerciseSetRepository;
import com.trainSync.workout.respository.WorkoutRepository;

/**
 * Author: Sajal Gupta Date: Nov 13, 2025
 */
@Service
public class WorkoutService {

	
	private final WorkoutRepository workoutRepository;
	private final ExerciseRepository exerciseRepository;
	private final ExerciseLibraryRepository exerciseLibraryRepository;
	private final PreMadeWorkoutSetRepository preMadeWorkoutSetRepository;
	
	
	WorkoutService(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository,
			ExerciseLibraryRepository exerciseLibraryRepository,
			PreMadeWorkoutSetRepository preMadeWorkoutSetRepository,
			ExerciseSetRepository exerciseSetRepository) {
		this.workoutRepository = workoutRepository;
		this.exerciseRepository = exerciseRepository;
		this.exerciseLibraryRepository = exerciseLibraryRepository;
		this.preMadeWorkoutSetRepository = preMadeWorkoutSetRepository;
	}
	

	/**
	 * 1. create and save workout
	 * 2. create and save exercise
	 * 3. pre populate sets if exercise has been done before
	 * @param workoutDto
	 * @param userId
	 * @return
	 */
	public String createWorkout(WorkoutDto workoutDto, UUID userId) {
		
		// Create and save workout (factory pattern)
		Workout workout = new WorkoutFactory().createNewBlankWorkout(workoutDto.getWorkoutName(),
				userId, OffsetDateTime.now());
		
		workoutRepository.save(workout);
		
		// Create and save exercise linked to workout
		ExerciseLibrary exerciseLib = exerciseLibraryRepository.findById(UUID.fromString(workoutDto.getExerciseId()))
				.get();

		Exercise lastExerciseForUser = exerciseRepository.findLatestExerciseForUser(userId, exerciseLib.getId());

		Exercise exercise = new ExerciseFactory().createExercise(exerciseLib, workout, 1); //order is always 1 for new workouts
		exerciseRepository.save(exercise);
		
		
		// ADD SETS FROM LAST TIME THIS EXERCISE WAS DONE
		List<ExerciseSet> sets = new ExerciseSetFactory().createSetsFromExistingExercise(lastExerciseForUser, exercise);
		exercise.setSets(sets);
		exerciseRepository.save(exercise);

		return workout.getId().toString();
	}


	/**
	 * 1. create exercise and save it
	 * 2. pre populate sets if done before
	 * @param workoutDto
	 * @param userId
	 * @return
	 */
	public String addExerciseToWorkout(WorkoutDto workoutDto, UUID userId) {
		// Create and save exercise linked to workout
		Workout workout = workoutRepository.findById(UUID.fromString(workoutDto.getWorkoutId())).get();
		ExerciseLibrary exerciseLib = exerciseLibraryRepository.findById(UUID.fromString(workoutDto.getExerciseId()))
				.get();
		Exercise lastExerciseForUser = exerciseRepository.findLatestExerciseForUser(userId, exerciseLib.getId());

		Exercise exercise = new ExerciseFactory().createExercise(exerciseLib, workout, workoutDto.getExerciseOrder());
		exerciseRepository.save(exercise);
		// ADD SETS FROM LAST TIME THIS EXERCISE WAS DONE
		List<ExerciseSet> sets = new ExerciseSetFactory().createSetsFromExistingExercise(
				lastExerciseForUser, exercise);
		exercise.setSets(sets);
		exerciseRepository.save(exercise);

		return workout.getId().toString();
	}

	/**
	 * @param preMade
	 * @param preMadeExercises
	 * @return
	 */
	public String createWorkoutUsingPreMade(PreMadeWorkout preMade, List<PreMadeWorkoutExercise> preMadeExercises,
			UUID userId) {
		
		Workout workout = new WorkoutFactory().createWorkoutFromPreMade( preMade.getName(),
				userId, OffsetDateTime.now(), preMade);
		workoutRepository.save(workout);
		
		// Excercises
		for (PreMadeWorkoutExercise preMadeExercise : preMadeExercises) {
			ExerciseLibrary exerciseLib = exerciseLibraryRepository.findById(preMadeExercise.getExercise().getId())
					.get();
			
			Exercise exercise = new ExerciseFactory().createExercise(exerciseLib, workout, preMadeExercise.getExerciseOrder());
			exerciseRepository.save(exercise);
			
			// SETS
			List<PreMadeWorkoutSet> preMadeSets = preMadeWorkoutSetRepository
					.findByPreMadeWorkoutExerciseId(preMadeExercise.getId());
			
			List<ExerciseSet> sets = new ExerciseSetFactory().createSetsFromPreMadeExercise( exercise, preMadeSets);
			
			exercise.setSets(sets);
			exerciseRepository.save(exercise);
		}

		return workout.getId().toString();
	}
	


	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(TrainSyncApplication.class, args);
		ExerciseRepository exerciseRepository = ctx.getBean(ExerciseRepository.class);
		Exercise lastExerciseForUser = exerciseRepository.findLatestExerciseForUser(
				UUID.fromString("5283d060-9816-413b-92f3-046ce2fdbc43"),
				UUID.fromString("a1363c95-b399-4907-9015-3ab6681113de"));
		System.out.println(lastExerciseForUser.getName());
	}
}
