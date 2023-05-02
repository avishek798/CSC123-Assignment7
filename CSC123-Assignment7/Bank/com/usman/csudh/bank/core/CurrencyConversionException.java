package com.usman.csudh.bank.core;

import java.io.Serializable;

public class CurrencyConversionException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public CurrencyConversionException(String message) {
		super(message);
	}

}
