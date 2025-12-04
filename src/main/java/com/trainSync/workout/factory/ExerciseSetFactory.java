
package com.trainSync.workout.factory;


import java.util.ArrayList;
import java.util.List;

import com.trainSync.preMadeWorkout.model.PreMadeWorkoutSet;
import com.trainSync.workout.model.Exercise;
import com.trainSync.workout.model.ExerciseSet;

/**
 * Author: Sajal Gupta
 * Created on: Dec 4, 2025 2:49:45 PM
 */
public class ExerciseSetFactory {
	
	public ExerciseSet createExerciseSet() {
		return null;
	}
	

	/**
	 * 
	 * @param exercise
	 * @param preMadeWorkoutExercise
	 * @return
	 */
	public List<ExerciseSet> createSetsFromPreMadeExercise(Exercise exercise,
			List<PreMadeWorkoutSet> preMadeSets) {
		List<ExerciseSet> sets = new ArrayList<>();
		for (var preMadeSet : preMadeSets) {
			ExerciseSet set = new ExerciseSet();
			set.setSetNumber(preMadeSet.getSetNumber());
			set.setExercise(exercise);
			sets.add(set);
		}
		return sets;

	}

	/**
	 * 
	 * @param lastExerciseForUser
	 * @param exercise
	 * @return
	 */
	public List<ExerciseSet> createSetsFromExistingExercise(Exercise lastExerciseForUser, Exercise exercise) {

		if (lastExerciseForUser == null) {
			return new ArrayList<>();
		}
		List<ExerciseSet> setsFromLast = lastExerciseForUser.getSets();
		List<ExerciseSet> setsToSave = new ArrayList<>();
		if (setsFromLast == null || setsFromLast.isEmpty()) {
			return new ArrayList<>();
		}
		for (var set : setsFromLast) {
			ExerciseSet newSet = new ExerciseSet();
			newSet.setSetNumber(set.getSetNumber());
			newSet.setExercise(exercise);
			newSet.setWeight(set.getWeight());
			newSet.setReps(set.getReps());
			setsToSave.add(newSet);
			exercise.setPreFilledFromLastWorkoutFlag("YES");
			exercise.setPreFilledWorkout(lastExerciseForUser.getWorkout());
		}

		return setsToSave;

	}

}
