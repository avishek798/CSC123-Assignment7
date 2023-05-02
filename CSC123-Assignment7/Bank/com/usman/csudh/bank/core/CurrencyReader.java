package com.usman.csudh.bank.core;

import java.io.FileNotFoundException;
import java.io.IOException;


public abstract class CurrencyReader {

	public CurrencyReader() {
		super();
	}

	public abstract String reader(String fileSource) throws FileNotFoundException, IOException, InterruptedException;

}
