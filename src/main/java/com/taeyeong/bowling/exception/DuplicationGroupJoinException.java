package com.taeyeong.bowling.exception;

public class DuplicationGroupJoinException extends RuntimeException{
	
private static final long serialVersionUID = 1L;
	
	public DuplicationGroupJoinException() {
		super();
	}
	
	public DuplicationGroupJoinException(String message) {
		super(message);
	}
	
	public DuplicationGroupJoinException(String message, Throwable th) {
		super(message, th);
	}

}
