
package com.trainSync.preMadeWorkout.factory;
/**
 * Author: Sajal Gupta
 * Created on: Dec 6, 2025 10:47:30 AM
 */

import com.trainSync.preMadeWorkout.model.PreMadeWorkout;
import com.trainSync.preMadeWorkout.model.PreMadeWorkoutExercise;
import com.trainSync.workout.model.ExerciseLibrary;

public class PreMadeWorkoutExerciseFactory {
	
	/**
	 * 
	 * @param preMadeWorkout
	 * @param exerciseOrder
	 * @param exerciseLib
	 * @return
	 */
	public PreMadeWorkoutExercise createPreMadeWorkoutExercise(PreMadeWorkout preMadeWorkout, int exerciseOrder, ExerciseLibrary exerciseLib) {
		return PreMadeWorkoutExercise.builder()
				.preMadeWorkout(preMadeWorkout)
				.exerciseOrder(exerciseOrder)
				.exercise(exerciseLib)
				.build();
	}
	
	
	

}
