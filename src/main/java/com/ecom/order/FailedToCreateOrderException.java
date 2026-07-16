package com.ecom.order;

public class FailedToCreateOrderException extends Exception {

	public FailedToCreateOrderException(String message) {
		super(message);
	}

	public FailedToCreateOrderException(String message, Throwable cause) {
		super(message, cause);
	}

}