
package com.trainSync.workout.factory;

import com.trainSync.workout.model.Exercise;
import com.trainSync.workout.model.ExerciseLibrary;
import com.trainSync.workout.model.Workout;

/**
 * Author: Sajal Gupta
 * Created on: Dec 4, 2025 2:44:40 PM
 */

public class ExerciseFactory {
	
	/**
	 * 
	 * @param exerciseLib
	 * @param workout
	 * @param exerciseOrder
	 * @return
	 */
	public Exercise createExercise(ExerciseLibrary exerciseLib, Workout workout, int exerciseOrder) {

		Exercise exercise = Exercise.builder()
                .workout(workout)
                .exerciseOrder(exerciseOrder)
                .exerciseLibrary(exerciseLib)
                .name(exerciseLib.getName())
                .build();
		
		return exercise;

	
	}

}
