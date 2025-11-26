
package com.trainSync.workout.dto;

/**
 * Author: Sajal Gupta Date: Nov 13, 2025
 */
public class SetDto {

	private String id;
	private double weight;
	private int reps;
	private int setNumber;
	
	public SetDto(String id, double weight, int reps, int setNumber) {
		this.id = id;
		this.weight = weight;
		this.reps = reps;
		setSetNumber(setNumber);
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the reps
	 */
	public int getReps() {
		return reps;
	}

	/**
	 * @param reps the reps to set
	 */
	public void setReps(int reps) {
		this.reps = reps;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the setNumber
	 */
	public int getSetNumber() {
		return setNumber;
	}

	/**
	 * @param setNumber the setNumber to set
	 */
	public void setSetNumber(int setNumber) {
		this.setNumber = setNumber;
	}

}
