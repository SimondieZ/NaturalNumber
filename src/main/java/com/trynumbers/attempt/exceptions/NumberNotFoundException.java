package com.trynumbers.attempt.exceptions;

public class NumberNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NumberNotFoundException(long id) {
		super("Could not find the number " + id);
	}

}
