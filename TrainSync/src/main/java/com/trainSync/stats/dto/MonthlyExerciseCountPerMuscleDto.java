package com.trainSync.stats.dto;

/**
 * @author sajalgupta
 */
public class MonthlyExerciseCountPerMuscleDto {

	private String muscleGroup;

	private int numberOfTimesWorked;

	private int numberOfSets;
	
	public MonthlyExerciseCountPerMuscleDto(String muscleGroup, long numberOfTimesWorked) {
        this.muscleGroup = muscleGroup;
        this.numberOfTimesWorked = (int) numberOfTimesWorked; // cast long → int
        this.numberOfSets = 0; // default
    }


	/**
	 * @return the muscleGroup
	 */
	public String getMuscleGroup() {
		return muscleGroup;
	}

	/**
	 * @param muscleGroup the muscleGroup to set
	 */
	public void setMuscleGroup(String muscleGroup) {
		this.muscleGroup = muscleGroup;
	}

	/**
	 * @return the numberOfTimesWorked
	 */
	public int getNumberOfTimesWorked() {
		return numberOfTimesWorked;
	}

	/**
	 * @param numberOfTimesWorked the numberOfTimesWorked to set
	 */
	public void setNumberOfTimesWorked(int numberOfTimesWorked) {
		this.numberOfTimesWorked = numberOfTimesWorked;
	}

	/**
	 * @return the numberOfSets
	 */
	public int getNumberOfSets() {
		return numberOfSets;
	}

	/**
	 * @param numberOfSets the numberOfSets to set
	 */
	public void setNumberOfSets(int numberOfSets) {
		this.numberOfSets = numberOfSets;
	}

}
