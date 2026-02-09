
package com.trainSync.workout.opensearch;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Feb 8, 2026 2:14:31 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseLibraryDocument {
	
	private String id;
	private String name;
	private String exercisePictureUrl;
	
	private Equipment equipment;
	
	private List<Muscle> muscles;
	
	@Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Equipment {
        private String id;
        private String name;
    }
	
	@Getter
	@Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Muscle {
		private String id;
        private String name;
        private String type; 
    }
	
	

}
