package com.taeyeong.bowling.exception;

public class GroupNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public GroupNotFoundException() {
		super();
	}
	
	public GroupNotFoundException(String message) {
		super(message);
	}
	
	public GroupNotFoundException(String message, Throwable th) {
		super(message, th);
	}
	
}
