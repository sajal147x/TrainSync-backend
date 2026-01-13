
package com.trainSync.config.exception;
/**
 * Author: Sajal Gupta
 * Created on: Jan 13, 2026 12:04:59 PM
 */

public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public UnauthorizedException(String message) {
        super(message);
    }
}