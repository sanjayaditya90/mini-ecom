package com.samsup.product;

public class FailedToAddProductException extends Exception {

	public FailedToAddProductException(String message) {
		super(message);
	}

	public FailedToAddProductException(String message, Throwable cause) {
		super(message, cause);
	}
}
