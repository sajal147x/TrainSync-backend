
package com.trainSync.objectionableContent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Feb 9, 2026 9:58:38 AM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlagContentRequest {
	
	private String groupId;
	private String text;
	private String userId;

}
