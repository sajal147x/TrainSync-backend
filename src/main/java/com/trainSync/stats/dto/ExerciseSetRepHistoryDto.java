
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
 * Created on: Jan 31, 2026 12:36:20 PM
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseSetRepHistoryDto {
	
	private String date;
	private List<SetDto> sets;

}
