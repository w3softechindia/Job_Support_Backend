package com.example.JobSupportBackend.exceptions;


public class InvalidIdException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidIdException(String msg) {
		super(msg);
	}
}
