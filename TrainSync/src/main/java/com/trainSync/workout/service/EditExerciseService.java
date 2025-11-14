package com.trainSync.workout.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainSync.workout.dto.EditExerciseDto;
import com.trainSync.workout.model.Exercise;
import com.trainSync.workout.model.ExerciseSet;
import com.trainSync.workout.respository.ExerciseRepository;
import com.trainSync.workout.respository.ExerciseSetRepository;


@Service
public class EditExerciseService {

	@Autowired
    private ExerciseRepository exerciseRepository;
	@Autowired
    private ExerciseSetRepository exerciseSetRepository;

    public EditExerciseService(
            ExerciseRepository exerciseRepository,
            ExerciseSetRepository exerciseSetRepository) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseSetRepository = exerciseSetRepository;
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

        exerciseSetRepository.save(set);
        
        return set.getId().toString();
    }

    // Edit set
    /**
     * 
     * @param dto
     */
    public void editExerciseSet(EditExerciseDto dto) {
    	System.out.println(" GOT SET " + dto.getSetId());
        ExerciseSet set = exerciseSetRepository.findById(UUID.fromString(dto.getSetId()))
                .orElseThrow(() -> new RuntimeException("Set not found"));

        set.setWeight(dto.getWeight());
        set.setReps(dto.getReps());

        exerciseSetRepository.save(set);
    }
}
