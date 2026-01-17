package com.trainSync.community.dto;

import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sajalgupta
 * 
 */
@Getter
@Setter
@Builder
public class GroupMessageDto {
	
	private OffsetDateTime sentAt;
	private String message;
	private String isSentByLoggedInUser;
	private FriendsResponseDto userDto;

}
