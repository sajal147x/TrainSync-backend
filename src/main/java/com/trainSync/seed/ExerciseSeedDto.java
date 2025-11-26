

package com.trainSync.seed;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: Sajal Gupta
 * Date: Nov 14, 2025
 */
public class ExerciseSeedDto {
	
	public String name;
	
	@JsonProperty("equipment")
    public List<String> equipment; 
	
	@JsonProperty("muscles")
    public List<MuscleTagSeedDto> muscleTags;

}
