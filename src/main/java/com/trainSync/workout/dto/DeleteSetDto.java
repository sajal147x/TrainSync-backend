
package com.trainSync.workout.dto;

import java.util.List;

/**
 * Author: Sajal Gupta
 * Date: Nov 17, 2025
 */

public class DeleteSetDto {
	
	private String deletedSetId;
	private List<SetDto> newSets;
	/**
	 * @return the deletedSetId
	 */
	public String getDeletedSetId() {
		return deletedSetId;
	}
	/**
	 * @param deletedSetId the deletedSetId to set
	 */
	public void setDeletedSetId(String deletedSetId) {
		this.deletedSetId = deletedSetId;
	}
	/**
	 * @return the newSets
	 */
	public List<SetDto> getNewSets() {
		return newSets;
	}
	/**
	 * @param newSets the newSets to set
	 */
	public void setNewSets(List<SetDto> newSets) {
		this.newSets = newSets;
	}

}
