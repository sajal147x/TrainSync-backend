
package com.trainSync.stats.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.trainSync.stats.dto.ExerciseCountDto;
import com.trainSync.stats.dto.ExerciseStats;
import com.trainSync.stats.dto.ExerciseStatsDto;
import com.trainSync.stats.dto.TopExerciseCount;
import com.trainSync.workout.dto.SetDto;
import com.trainSync.workout.model.ExerciseSet;
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

	/**
	 * @desc method for computing stats to provide insights to user for a specific exercise
	 * @param userId
	 * @param fromString
	 * @return
	 */
	public ExerciseStatsDto computeExerciseStats(UUID userId, UUID exerciseLibraryId) {
		
		ExerciseStats basicStats = exerciseRepository.getExerciseStats(userId, exerciseLibraryId);
		
		//BASIC STATS
		ExerciseStatsDto statsDto = ExerciseStatsDto.builder()
				.totalCount(basicStats.getTotalCount())
				.averageNumberOfSets(basicStats.getAvgSets())
				.maxWeight(basicStats.getMaxWeight())
				.build();
		
		//REPS FOR MAX WEIGHT
		Integer repsForMaxWeight = exerciseRepository.findHighestRepsAtMaxWeight(userId, exerciseLibraryId);
		statsDto.setRepsForMaxWeight(repsForMaxWeight);
		
		//RECOMMENDED SET/REPS
		
		return statsDto;
		
		
	}
	
	public List<SetDto> createRecommendedSetsBasedOnExistingSets(List<ExerciseSet> existingSets, double weightIncreasePercentage, double repsIncreasePercentage) {
		return null;
	}
	
}
