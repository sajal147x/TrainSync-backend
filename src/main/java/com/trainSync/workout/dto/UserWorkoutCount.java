
package com.trainSync.workout.dto;

import java.util.UUID;

/**
 * Author: Sajal Gupta
 * Created on: Jan 15, 2026 10:57:03 AM
 */

public interface UserWorkoutCount {
    UUID getUserId();
    Long getWorkoutCount();
}
