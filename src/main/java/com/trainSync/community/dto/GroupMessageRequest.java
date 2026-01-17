package com.trainSync.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder

/**
 * 
 * @author sajalgupta
 * 
 */
public class GroupMessageRequest {
	
	private String groupId;
	private String message;

}
