package com.ecom.user;

public class FailedToCreateUserException extends Exception {

	public FailedToCreateUserException(String message) {
		super(message);
	}

	public FailedToCreateUserException(String message, Throwable cause) {
		super(message, cause);
	}

}
