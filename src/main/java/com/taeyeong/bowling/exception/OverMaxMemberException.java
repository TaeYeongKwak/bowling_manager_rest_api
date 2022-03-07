package com.taeyeong.bowling.exception;

public class OverMaxMemberException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public OverMaxMemberException() {
		super();
	}
	
	public OverMaxMemberException(String message) {
		super(message);
	}
	
	public OverMaxMemberException(String message, Throwable th) {
		super(message, th);
	}
	
}
