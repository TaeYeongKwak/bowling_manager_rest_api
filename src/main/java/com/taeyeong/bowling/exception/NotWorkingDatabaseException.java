package com.taeyeong.bowling.exception;

public class NotWorkingDatabaseException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public NotWorkingDatabaseException() {
		super();
	}
	
	public NotWorkingDatabaseException(String message) {
		super(message);
	}
	
	public NotWorkingDatabaseException(String message, Throwable th) {
		super(message, th);
	}

}
