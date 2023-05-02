package com.usman.csudh.bank.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileCurrency extends CurrencyReader {

	public FileCurrency() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String reader(String fileSource) throws FileNotFoundException, IOException {
		String line="";
		String s;
		
		File f = new File (fileSource);
		
		if (!f.exists()) {
			throw new IOException("** Currency file could not be loaded, "
					+ "Currency Conversion Service and Foreign Currency accounts are not available **%n");
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		while ((s = reader.readLine()) != null) {
			line += s+"\n";
		}
		reader.close();
		return line;
	}

}
