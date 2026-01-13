
package com.trainSync.community.dto;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Jan 13, 2026 11:46:18 AM
 */

@Builder
@Getter
@Setter
public class FriendGroupCreateDto {
	
	@NotBlank(message = "Group name must not be blank")
	private String groupName;
	
	@NotEmpty(message = "Member IDs list must not be empty")
	private List<String> memberIds;
	
	
	

}
