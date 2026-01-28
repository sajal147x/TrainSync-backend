
package com.trainSync.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Jan 27, 2026 3:46:59 PM
 */
@Getter
@Setter
public class LogoutRequest {
	
	private String refreshToken;
	private String pushNotificationToken;
}
