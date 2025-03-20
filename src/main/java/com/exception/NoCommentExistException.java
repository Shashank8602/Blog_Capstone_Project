package com.exception;

public class NoCommentExistException extends RuntimeException {
	public NoCommentExistException(String message) {
		super(message);
	}
}

/*
Custom exception for no comments found.
Extends RuntimeException.
Thrown when no comments exist for a given blog.
*/
