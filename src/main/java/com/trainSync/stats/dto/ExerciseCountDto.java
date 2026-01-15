
package com.trainSync.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Jan 15, 2026 5:13:39 PM
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseCountDto {
	private String exerciseLibraryId;
	private String exerciseName;
	private String exercisePictureUrl;
	private int exerciseCount;

}
