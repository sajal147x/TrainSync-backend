
package com.trainSync.workout.dto;

/**
 * Author: Sajal Gupta
 * Date: Nov 14, 2025
 */

public class MuscleTagDto {
	
	private String id;
	
	private String name;
	private String level;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

}
