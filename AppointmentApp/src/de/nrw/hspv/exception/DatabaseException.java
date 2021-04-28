package de.nrw.hspv.exception;

import java.io.IOException;

public class DatabaseException extends IOException{
	
	private static final long serialVersionUID = 2L;

	public DatabaseException() {
		super("DatabaseException");
	}
	
	public DatabaseException(String s) {
		super(s);
	}

}
