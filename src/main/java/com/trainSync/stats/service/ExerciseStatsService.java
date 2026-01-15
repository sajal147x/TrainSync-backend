
package com.trainSync.stats.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.trainSync.stats.dto.ExerciseCountDto;
import com.trainSync.stats.dto.TopExerciseCount;
import com.trainSync.workout.respository.ExerciseRepository;

/**
 * Author: Sajal Gupta
 * Created on: Jan 15, 2026 5:16:41 PM
 */
@Service
public class ExerciseStatsService {
	
	private final ExerciseRepository exerciseRepository;
	
	public ExerciseStatsService(ExerciseRepository exerciseRepository) {
		this.exerciseRepository = exerciseRepository;
	}

	/**
	 * get top 5 most performed exercises by user
	 * convert to DTO
	 * @param userId
	 * @return
	 */
	public List<ExerciseCountDto> getMostPerformedExercises(UUID userId) {
		List<TopExerciseCount> topExercises = exerciseRepository.findTopExercisesForUser(userId, PageRequest.of(0, 5));
		
		// Convert to DTO
		List<ExerciseCountDto> dtoList = new ArrayList<>();
		for(TopExerciseCount tec : topExercises) {
			ExerciseCountDto dto = new ExerciseCountDto();
			dto.setExerciseLibraryId(tec.getExerciseLibraryId().toString());
			dto.setExerciseName(tec.getExerciseName());
			dto.setExerciseCount(tec.getCount());
			dto.setExercisePictureUrl(tec.getExercisePictureUrl());
			dtoList.add(dto);
		}
		return dtoList;
		
	}

}
