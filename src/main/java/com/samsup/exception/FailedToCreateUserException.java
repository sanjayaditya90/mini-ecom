package com.samsup.exception;

public class FailedToCreateUserException extends Exception {

	public FailedToCreateUserException(String message) {
		super(message);
	}

	public FailedToCreateUserException(String message, Throwable cause) {
		super(message, cause);
	}

}
