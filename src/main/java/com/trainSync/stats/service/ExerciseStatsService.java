
package com.trainSync.stats.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.trainSync.stats.dto.ExerciseCountDto;
import com.trainSync.stats.dto.ExerciseStatTimeFrame;
import com.trainSync.stats.dto.ExerciseStatTimeFrameDto;
import com.trainSync.stats.dto.ExerciseStats;
import com.trainSync.stats.dto.ExerciseStatsDto;
import com.trainSync.stats.dto.TopExerciseCount;
import com.trainSync.util.Constants;
import com.trainSync.util.DateUtility;
import com.trainSync.workout.dto.SetDto;
import com.trainSync.workout.model.Exercise;
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
	 * @desc method for computing progression for an exercise
	 * @param userId
	 * @param timeFrameMonths
	 * @param statType
	 * @param fromString
	 * @return
	 */
	public ExerciseStatsDto computeExerciseProgression(UUID userId, UUID exerciseLibraryId, String statType,
			String timeFrameMonths) {
		
		ExerciseStatsDto resultDto = new ExerciseStatsDto();
		//DETERMINE CUT OFF DATE 
		OffsetDateTime cutOff = DateUtility.getCutOffDateTimeFromMonthsAgo(timeFrameMonths);

		//IF VOLUME STAT TYPE
		if (statType.equals(Constants.STAT_TYPE_TOTAL_VOLUME)) {
			List<ExerciseStatTimeFrame> statTimeFrames = exerciseRepository.findExerciseVolumeHistory(userId,
					exerciseLibraryId, cutOff);
			List<ExerciseStatTimeFrameDto> dtoTimeFrames = new ArrayList<>();

			for (ExerciseStatTimeFrame estf : statTimeFrames) {
				ExerciseStatTimeFrameDto dto = ExerciseStatTimeFrameDto.builder().workoutDate(estf.getWorkoutDate())
						.statValue(estf.getStatValue()).build();
				dtoTimeFrames.add(dto);
			}
			resultDto.setExerciseStatTimeFrames(dtoTimeFrames);

		}
		
		
		return resultDto;
	}
	
}
