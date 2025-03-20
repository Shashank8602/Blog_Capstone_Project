package com.exception;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String message) {
		super(message);
	}
}

/*
Custom exception for resource not found.
Extends RuntimeException.
Thrown when a requested resource is not found.
*/
