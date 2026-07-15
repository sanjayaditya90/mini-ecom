package com.samsup.exception;

public class FailedToGetUserException extends Exception {
	public FailedToGetUserException(String message) {
		super(message);
	}

	public FailedToGetUserException(String message, Throwable cause) {
		super(message, cause);
	}
}
