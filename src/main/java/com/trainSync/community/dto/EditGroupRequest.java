
package com.trainSync.community.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Jan 15, 2026 3:26:13 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditGroupRequest {
	
	
	private String groupId;
	private String profilePictureBase64;
	private List<String> toRemoveUserIds;
	

}
