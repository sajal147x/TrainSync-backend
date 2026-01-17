
package com.trainSync.stats.dto;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Jan 17, 2026 2:07:07 PM
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseStatTimeFrameDto {
	
	Double statValue;
	OffsetDateTime workoutDate;

}
