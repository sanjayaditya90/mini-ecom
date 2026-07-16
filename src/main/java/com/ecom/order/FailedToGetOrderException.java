package com.ecom.order;

public class FailedToGetOrderException extends Exception {

	public FailedToGetOrderException(String message) {
		super(message);
	}

	public FailedToGetOrderException(String message, Throwable cause) {
		super(message, cause);
	}

}