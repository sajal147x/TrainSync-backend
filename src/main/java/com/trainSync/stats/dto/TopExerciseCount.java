
package com.trainSync.stats.dto;

import java.util.UUID;

/**
 * Author: Sajal Gupta
 * Created on: Jan 15, 2026 5:26:33 PM
 */
public interface TopExerciseCount {
	UUID getExerciseLibraryId();
	String getExerciseName();
	int getCount();
	String getExercisePictureUrl();

}
