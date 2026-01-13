
package com.trainSync.config.exception;

import java.time.OffsetDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Sajal Gupta
 * Created on: Jan 13, 2026 11:58:11 AM
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {
	ApiError(String code, String message, OffsetDateTime timestamp) {
		this.code = code;
		this.message = message;
		this.timestamp = timestamp;
	}
	private String code;
	private String message;
	private Map<String, String> errors;
	private OffsetDateTime timestamp;

}
