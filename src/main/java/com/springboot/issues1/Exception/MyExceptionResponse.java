package com.springboot.issues1.Exception;

public class MyExceptionResponse {
	private String exception;

	public MyExceptionResponse(String exception) {
		super();
		this.exception = exception;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

}
