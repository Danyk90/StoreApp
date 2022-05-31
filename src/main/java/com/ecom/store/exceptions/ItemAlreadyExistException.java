package com.ecom.store.exceptions;

public class ItemAlreadyExistException extends RuntimeException {

	public ItemAlreadyExistException() {
	}

	public ItemAlreadyExistException(String message) {
		super(message);
	}

	public ItemAlreadyExistException(Throwable cause) {
		super(cause);
	}

	public ItemAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public ItemAlreadyExistException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
