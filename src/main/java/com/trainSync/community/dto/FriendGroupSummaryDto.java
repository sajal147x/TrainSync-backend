
package com.trainSync.community.dto;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Author: Sajal Gupta
 * Created on: Jan 13, 2026 1:13:59 PM
 */
@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendGroupSummaryDto {
	
	private String groupId;
	private String groupName;
	private String profilePictureUrl;

}
