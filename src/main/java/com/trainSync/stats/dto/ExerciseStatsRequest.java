
package com.trainSync.stats.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Jan 17, 2026 1:25:52 PM
 */
@Getter
@Setter
public class ExerciseStatsRequest {
	private String exerciseLibraryId;
	private String timeFrameMonths;
	private String statType;

}
