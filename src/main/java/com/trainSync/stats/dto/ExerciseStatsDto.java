
package com.trainSync.stats.dto;

import java.util.List;

import com.trainSync.workout.dto.SetDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Jan 16, 2026 10:33:21 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseStatsDto {
	
	private int totalCount;
	private int averageNumberOfSets;
	private Double maxWeight;
	private int repsForMaxWeight;
	private List<SetDto> recommendedSets;

}
