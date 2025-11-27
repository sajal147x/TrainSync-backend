
package com.trainSync.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Nov 27, 2025 12:39:34 PM
 */
@Getter
@Setter
@Builder
public class UserSearchDto {
	private String userId;
	private String userName;

}
