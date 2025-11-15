
package com.trainSync.stats.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainSync.stats.dto.ExerciseProgressionDto;
import com.trainSync.workout.model.Exercise;
import com.trainSync.workout.model.ExerciseSet;
import com.trainSync.workout.respository.ExerciseRepository;

/**
 * Author: Sajal Gupta Date: Nov 14, 2025
 */
@Service
public class ExerciseProgressionService {
	
	@Autowired
	ExerciseRepository exerciseRepository;

	/**
	 * temp method just to test graphs
	 * @param userId
	 * @param exerciseId
	 * @return
	 */
	public List<ExerciseProgressionDto> computeExerciseTonnageProgression(UUID userId, UUID exerciseId) {
		// Fetch all matching exercises from DB
		List<Exercise> exercises = exerciseRepository.findByUserAndExerciseLibraryId(userId, exerciseId);
		List<ExerciseProgressionDto> result = new ArrayList<>();
		for (Exercise e : exercises) {
			double tonnage = 0.0;
			if (e.getSets() != null) {
				for (ExerciseSet set : e.getSets()) {
					tonnage += set.getWeight() * set.getReps();
				}
			}
			ExerciseProgressionDto dto = new ExerciseProgressionDto();
			dto.setDate(e.getWorkout().getStartTime().toString());
			dto.setTonnage(tonnage);
			result.add(dto);
		}

		return result;

	}
	
	public static void main(String[] args) {
		
	}

}
