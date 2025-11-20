package com.trainSync.workout.service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.trainSync.TrainSyncApplication;
import com.trainSync.preMadeWorkout.model.PreMadeWorkout;
import com.trainSync.preMadeWorkout.model.PreMadeWorkoutExercise;
import com.trainSync.preMadeWorkout.model.PreMadeWorkoutSet;
import com.trainSync.preMadeWorkout.repository.PreMadeWorkoutSetRepository;
import com.trainSync.workout.dto.WorkoutDto;
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

	@Autowired
	private WorkoutRepository workoutRepository;

	@Autowired
	private ExerciseRepository exerciseRepository;

	@Autowired
	private ExerciseLibraryRepository exerciseLibraryRepository;
	
	@Autowired
	private PreMadeWorkoutSetRepository preMadeWorkoutSetRepository;
	
	@Autowired
	private ExerciseSetRepository exerciseSetRepository;

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
		Exercise lastExerciseForUser = exerciseRepository.findLatestExerciseForUser(userId, exerciseLib.getId());
		exerciseRepository.save(exercise);
		//ADD SETS FROM LAST TIME THIS EXERCISE WAS DONE
		createSetsBasedOnLastWorkoutForExercise(lastExerciseForUser, exercise, exerciseLib.getId(), userId);

		return workout.getId().toString();
	}


	/**
	 * @param workoutDto
	 * @param userId
	 * @return
	 */
	public String addExerciseToWorkout(WorkoutDto workoutDto, UUID userId) {
		// Create and save exercise linked to workout
		Exercise exercise = new Exercise();
		Workout workout = workoutRepository.findById(UUID.fromString(workoutDto.getWorkoutId())).get();
		exercise.setWorkout(workout);
		ExerciseLibrary exerciseLib = exerciseLibraryRepository.findById(UUID.fromString(workoutDto.getExerciseId()))
				.get();
		exercise.setExerciseLibraryId(UUID.fromString(workoutDto.getExerciseId()));
		exercise.setName(exerciseLib.getName());
		Exercise lastExerciseForUser = exerciseRepository.findLatestExerciseForUser(userId, exerciseLib.getId());
		exerciseRepository.save(exercise);

		// ADD SETS FROM LAST TIME THIS EXERCISE WAS DONE
		createSetsBasedOnLastWorkoutForExercise(lastExerciseForUser, exercise, exerciseLib.getId(), userId);
		return workout.getId().toString();
	}

	/**
	 * @param preMade
	 * @param preMadeExercises
	 * @return
	 */
	public String createWorkoutUsingPreMade(PreMadeWorkout preMade, List<PreMadeWorkoutExercise> preMadeExercises,
			UUID userId) {
		OffsetDateTime dateTime = OffsetDateTime.now();
		Workout workout = new Workout();
		workout.setName(preMade.getName());
		workout.setStartTime(dateTime);
		workout.setUserId(userId);
		workoutRepository.save(workout);

		// Excercises
		for (PreMadeWorkoutExercise preMadeExercise : preMadeExercises) {
			Exercise exercise = new Exercise();
			exercise.setWorkout(workout);
			ExerciseLibrary exerciseLib = exerciseLibraryRepository.findById(preMadeExercise.getExercise().getId())
					.get();
			exercise.setExerciseLibraryId(exerciseLib.getId());
			exercise.setName(exerciseLib.getName());
			// SETS
			List<PreMadeWorkoutSet> preMadeSets = preMadeWorkoutSetRepository
					.findByPreMadeWorkoutExerciseId(preMadeExercise.getId());
			List<ExerciseSet> sets = new ArrayList<ExerciseSet>();
			for (var preMadeSet : preMadeSets) {
				ExerciseSet set = new ExerciseSet();
				set.setSetNumber(preMadeSet.getSetNumber());
				set.setExercise(exercise);
				sets.add(set);
			}
			exercise.setSets(sets);
			exerciseRepository.save(exercise);
		}

		return workout.getId().toString();
	}
	
	
	/**
	 * @param exercise 
	 * @param id
	 * @param userId
	 * @return
	 */
	private void createSetsBasedOnLastWorkoutForExercise(Exercise lastExerciseForUser, Exercise exercise,
			UUID exerciseLibId, UUID userId) {
		if (lastExerciseForUser == null) {
			return;
		}
		List<ExerciseSet> setsFromLast = lastExerciseForUser.getSets();
		List<ExerciseSet> setsToSave = new ArrayList<ExerciseSet>();
		if (setsFromLast == null || setsFromLast.isEmpty()) {
			return;
		}
		for (var set : setsFromLast) {
			ExerciseSet newSet = new ExerciseSet();
			newSet.setSetNumber(set.getSetNumber());
			newSet.setExercise(exercise);
			newSet.setWeight(set.getWeight());
			newSet.setReps(set.getReps());
			setsToSave.add(newSet);
			exercise.setPreFilledFromLastWorkoutFlag("YES");
			exercise.setPreFilledWorkoutDate(lastExerciseForUser.getWorkout().getStartTime());
		}
		exerciseRepository.save(exercise);
		exerciseSetRepository.saveAll(setsToSave);
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
