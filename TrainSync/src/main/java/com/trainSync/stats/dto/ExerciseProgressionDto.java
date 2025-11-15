
package com.trainSync.stats.dto;

/**
 * Author: Sajal Gupta Date: Nov 14, 2025
 */
public class ExerciseProgressionDto {

	private String date;
	
	private double tonnage;

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the tonnage
	 */
	public double getTonnage() {
		return tonnage;
	}

	/**
	 * @param tonnage the tonnage to set
	 */
	public void setTonnage(double tonnage) {
		this.tonnage = tonnage;
	}


}
