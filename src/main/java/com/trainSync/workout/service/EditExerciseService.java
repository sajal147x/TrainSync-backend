package com.trainSync.workout.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trainSync.workout.dto.EditExerciseDto;
import com.trainSync.workout.factory.ExerciseSetFactory;
import com.trainSync.workout.model.Exercise;
import com.trainSync.workout.model.ExerciseLibrary;
import com.trainSync.workout.model.ExerciseSet;
import com.trainSync.workout.respository.ExerciseLibraryRepository;
import com.trainSync.workout.respository.ExerciseRepository;
import com.trainSync.workout.respository.ExerciseSetRepository;


/**
 * @author sajalgupta
 * @date nov 13, 2025
 */
@Service
public class EditExerciseService {

	
    private final ExerciseRepository exerciseRepository;
	
    private final ExerciseSetRepository exerciseSetRepository;
    
    private final ExerciseLibraryRepository exerciseLibraryRepository;

    public EditExerciseService(
            ExerciseRepository exerciseRepository,
            ExerciseSetRepository exerciseSetRepository, ExerciseLibraryRepository exerciseLibraryRepository) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseSetRepository = exerciseSetRepository;
        this.exerciseLibraryRepository = exerciseLibraryRepository;
    }

    // Add set
    /**
     * 
     * @param dto
     */
    public String addSetToExercise(EditExerciseDto dto) {
        Exercise exercise = exerciseRepository.findById(UUID.fromString(dto.getExerciseId()))
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        ExerciseSet set = new ExerciseSet();
        set.setExercise(exercise);
        set.setWeight(dto.getWeight());
        set.setReps(dto.getReps());
        set.setSetNumber(dto.getSetNumber());
        
        exercise.setEditedFlag("YES");
        
        exerciseRepository.save(exercise);
        exerciseSetRepository.save(set);
        
        return set.getId().toString();
    }

    // Edit set
    /**
     * 
     * @param dto
     */
    public void editExerciseSet(EditExerciseDto dto) {
        ExerciseSet set = exerciseSetRepository.findById(UUID.fromString(dto.getSetId()))
                .orElseThrow(() -> new RuntimeException("Set not found"));

        set.setWeight(dto.getWeight());
        set.setReps(dto.getReps());

        exerciseSetRepository.save(set);
        
        Exercise exercise = set.getExercise();
        exercise.setEditedFlag("YES");
        exerciseRepository.save(exercise);
    }
    
    /**
     * 1. delete sets if exist
     * 2. delete exercise
     * @param fromString
     */
	public void deleteExerciseInWorkout(UUID exerciseId) {
		Exercise exercise = exerciseRepository.findById(exerciseId).get();
		List<ExerciseSet> sets = exercise.getSets();
		if (sets != null && !sets.isEmpty()) {
			exerciseSetRepository.deleteAll(sets);
		}
		exerciseRepository.delete(exercise);
		
	}

	/**
	 * 
	 * @param exerciseId
	 * @param exerciseLibraryId
	 */
	public void changeExerciseInWorkout(UUID exerciseId, UUID exerciseLibraryId) {
		
		Exercise exercise = exerciseRepository.findById(exerciseId).get();
		//last exercise has to be fetched before changing the exercise id
		Exercise lastExerciseForUser = exerciseRepository.findLatestExerciseForUser(exercise.getWorkout().getUserId(), exerciseLibraryId);
		ExerciseLibrary exerciseLibrary = exerciseLibraryRepository
				.findById(exerciseLibraryId).get();
		exercise.setExerciseLibrary(exerciseLibrary);
		
		// if exercise has not been edited then need to change the sets to match the last time user performed the new exercise
		if ("NO".equals(exercise.getEditedFlag())) {
			//DELETE EXISTING SETS
			exercise.getSets().clear();
			exerciseRepository.saveAndFlush(exercise);
			// ADD SETS FROM LAST TIME THIS EXERCISE WAS DONE
			List<ExerciseSet> sets = new ExerciseSetFactory().createSetsFromExistingExercise(
					lastExerciseForUser, exercise);
			exercise.getSets().addAll(sets); 
		}
		
		exerciseRepository.save(exercise);
	}
}
