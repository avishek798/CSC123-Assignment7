package com.usman.csudh.bank.core;

public class ConfigFile {

	private String support;
	private String currenciesSource;
	private String websiteURL;
	private String fileName;

	public ConfigFile(String support, String currenciesSource, String websiteURL, String fileName) {
		super();
		this.support = support;
		this.currenciesSource = currenciesSource;
		this.websiteURL = websiteURL;
		this.fileName = fileName;
	}

	public String isSupport() {
		return support;
	}

	public String getCurrenciesSource() {
		return currenciesSource;
	}

	public String getWebsiteURL() {
		return websiteURL;
	}

	public String getFileName() {
		return fileName;
	}

}
