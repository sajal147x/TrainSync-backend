
package com.trainSync.preMadeWorkout.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainSync.preMadeWorkout.model.PreMadeWorkoutSet;
import com.trainSync.preMadeWorkout.repository.PreMadeWorkoutExerciseRepository;
import com.trainSync.preMadeWorkout.repository.PreMadeWorkoutSetRepository;

/**
 * Author: Sajal Gupta
 * Date: Nov 18, 2025
 */
@Service
public class EditPreMadeExerciseService {
	
	@Autowired
	PreMadeWorkoutExerciseRepository preMadeWorkoutExerciseRepository;
	
	@Autowired
	PreMadeWorkoutSetRepository preMadeWorkoutSetRepository;

	/**
	 * @param exerciseId
	 * @param setNumber
	 * @return
	 */
	public String addSetToExercise(String exerciseId, int setNumber) {

		PreMadeWorkoutSet set = new PreMadeWorkoutSet();
		set.setPreMadeWorkoutExercise(preMadeWorkoutExerciseRepository.findById(UUID.fromString(exerciseId)).get());
		set.setSetNumber(setNumber);
		preMadeWorkoutSetRepository.save(set);
		
		return set.getId().toString();

	}

}
