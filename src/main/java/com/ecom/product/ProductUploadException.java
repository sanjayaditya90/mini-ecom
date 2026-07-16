package com.ecom.product;

public class ProductUploadException extends Exception {

	public ProductUploadException(String message) {
		super(message);
	}

	public ProductUploadException(String message, Throwable cause) {
		super(message, cause);
	}

}
