
package com.trainSync.preMadeWorkout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Dec 6, 2025 11:10:45 AM
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvertWorkoutToPreMadeDto {
	private String workoutId;
	private String name;

}
