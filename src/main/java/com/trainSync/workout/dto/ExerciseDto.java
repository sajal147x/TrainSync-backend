package com.trainSync.workout.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta Date: Nov 12, 2025
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDto {
	private String id;
	private String name;
	private List<MuscleTagDto> muscleTags = new ArrayList<MuscleTagDto>();
	private List<SetDto> sets = new ArrayList<SetDto>();
	private EquipmentTagDto equipmentTag = new EquipmentTagDto();
	private String preFilledFlag;
	private String preFilledDate;
	private String preFilledWorkoutName;
	private String exercisePictureUrl;
	private int exerciseOrder;
	private String exerciseLibraryId;
}
