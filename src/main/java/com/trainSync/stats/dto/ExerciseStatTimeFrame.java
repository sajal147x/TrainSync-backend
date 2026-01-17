
package com.trainSync.stats.dto;
/**
 * Author: Sajal Gupta
 * Created on: Jan 17, 2026 2:25:50 PM
 */

import java.time.OffsetDateTime;

public interface ExerciseStatTimeFrame {
	
	OffsetDateTime getWorkoutDate();
	Double getStatValue();

}
