package com.usman.csudh.bank.core;
import java.io.Serializable;

public class AccountClosedException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;

	
	public AccountClosedException() {
		// TODO Auto-generated constructor stub
	}

	public AccountClosedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AccountClosedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public AccountClosedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AccountClosedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}