package com.usman.csudh.bank.core;

import java.io.IOException;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.StringReader;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Bank {

	private static Map<Integer, Account> accounts = new TreeMap<Integer, Account>();
	private static Map<String, Currency> currencies = new TreeMap<String, Currency>();
	private static Map<String, String> config = new TreeMap<String, String>();

	private static ConfigFile configObj;

	public static void readConfigFile() throws IOException {
		reader = new BufferedReader(new InputStreamReader(new FileInputStream((new File("config.txt")))));
		String s;
		String[] content;
		while ((s = reader.readLine()) != null) {
			content = s.split("=");
			config.put(content[0], content[1]);
		}

		configObj = new ConfigFile(config.get("support.currencies"), config.get("currencies.source"),
				config.get("webservice.url"), config.get("currency.file"));
	}

	static BufferedReader reader = null;

	public static void readCurrency() throws FileNotFoundException, IOException, InterruptedException {

		currencies.put("USD", new Currency("USD", "United States dollar", 1));
		if (configObj.isSupport().equalsIgnoreCase("true")) {
			if (configObj.getCurrenciesSource().equalsIgnoreCase("file")) {
				CurrencyReader cr = new FileCurrency();
				String line;
				reader = new BufferedReader(new StringReader(cr.reader(configObj.getFileName())));
				while ((line = reader.readLine()) != null) {
					String[] content = line.split(",");
					Currency currency = new Currency(content[0], content[1], Double.parseDouble(content[2]));

					currencies.put(content[0].toUpperCase(), currency);

				}
			} else if (configObj.getCurrenciesSource().equalsIgnoreCase("webservice")) {
				CurrencyReader cr = new WebCurrency();
				String line;
				reader = new BufferedReader(new StringReader(cr.reader(configObj.getWebsiteURL())));
				while ((line = reader.readLine()) != null) {
					String[] content = line.split(",");
					Currency currency = new Currency(content[0], content[1], Double.parseDouble(content[2]));

					currencies.put(content[0].toUpperCase(), currency);

				}
			} else {
				throw new IOException("** Currency could not be loaded, "
						+ "Currency Conversion Service and Foreign Currency accounts are not available **%n");
			}
		}
	}

	public static boolean fileloaded() {
		return currencies.size() > 1;
	}

	public static int fileSize() {
		return currencies.size();
	}

	public static Object[] currencyConversion(String sellCurrency, String buyCurrency, double amount)
			throws IOException, CurrencyConversionException {
		if (!fileloaded()) {
			throw new IOException("\nCurrency Conversion Service Unavailable.\n\n");
		}

		if (!(sellCurrency.toUpperCase().equals("USD") || buyCurrency.toUpperCase().equals("USD"))) {
			throw new CurrencyConversionException("\nError. One of the currencies must be USD.\n");

		}

		if (!(currencies.containsKey(sellCurrency.toUpperCase())
				&& currencies.containsKey(buyCurrency.toUpperCase()))) {
			throw new CurrencyConversionException("\nError. Exchange rate not found!\n");

		}

		double calcAmount = 0;
		if (sellCurrency.equalsIgnoreCase("USD")) {

			calcAmount = amount / (currencies.get((buyCurrency.toUpperCase())).getExRate());
			return new Object[] { currencies.get(buyCurrency.toUpperCase()).getExRate(), buyCurrency.toUpperCase(),
					String.format("%.2f", calcAmount) };

		}

		else if (!(sellCurrency.equalsIgnoreCase("USD"))) {
			calcAmount = amount * (currencies.get(sellCurrency.toUpperCase())).getExRate();
			return new Object[] { currencies.get(sellCurrency.toUpperCase()).getExRate(), buyCurrency.toUpperCase(),
					String.format("%.2f", calcAmount) };

		}

		return new Object[] {};
	}

	public static Account openCheckingAccount(String firstName, String lastName, String ssn, double overdraftLimit,
			String currencyCode) throws CurrencyConversionException {
		Customer c = new Customer(firstName, lastName, ssn);

		if (!(currencies.containsKey(currencyCode.toUpperCase()))) {
			throw new CurrencyConversionException(
					"\nCurrency not availble. Re-enter another currency when opening a new account.\n");
		}

		Account a = new CheckingAccount(c, overdraftLimit, currencies.get(currencyCode.toUpperCase()));

		accounts.put(a.getAccountNumber(), a);
		return a;

	}

	public static Account openSavingAccount(String firstName, String lastName, String ssn, String currencyCode)
			throws CurrencyConversionException {

		Customer c = new Customer(firstName, lastName, ssn);

		if (!(currencies.containsKey(currencyCode.toUpperCase()))) {
			throw new CurrencyConversionException(
					"\nCurrency not availble. Re-enter another currency when opening a new account.\n");
		}
		Account a = new SavingAccount(c, currencies.get(currencyCode.toUpperCase()));
		accounts.put(a.getAccountNumber(), a);
		return a;

	}

	public static Account lookup(int accountNumber) throws NoSuchAccountException {
		if (!accounts.containsKey(accountNumber)) {
			throw new NoSuchAccountException("\nAccount number: " + accountNumber + " not found!\n\n");
		}

		return accounts.get(accountNumber);
	}

	public static void makeDeposit(int accountNumber, double amount)
			throws AccountClosedException, NoSuchAccountException {

		lookup(accountNumber).deposit(amount);

	}

	public static void makeWithdrawal(int accountNumber, double amount)
			throws InsufficientBalanceException, NoSuchAccountException {
		lookup(accountNumber).withdraw(amount);
	}

	public static void closeAccount(int accountNumber) throws NoSuchAccountException {
		lookup(accountNumber).close();
	}

	public static double getBalance(int accountNumber) throws NoSuchAccountException {
		return lookup(accountNumber).getBalance();
	}

	public static void listAccounts(OutputStream out) throws IOException {

		out.write((byte) 10);
		Collection<Account> col = accounts.values();

		for (Account a : col) {
			out.write(a.toString().getBytes());
			out.write((byte) 10);
		}

		out.write((byte) 10);
		out.flush();
	}

	public static void showAccInformation(int accountNumber, OutputStream out)
			throws NoSuchAccountException, IOException {
		if (!accounts.containsKey(accountNumber)) {
			throw new NoSuchAccountException("\nAccount number: " + accountNumber + " not found!\n\n");
		}

		Account a = lookup(accountNumber);
		out.write((byte) 10);
		out.write(("Account Number: " + a.getAccountNumber() + "\n").getBytes());
		out.write(("Name: " + a.getAccountHolderName() + "\n").getBytes());
		out.write(("SSN: " + a.getAccountHolderSSN() + "\n").getBytes());
		out.write(("Currency: " + a.getCurrencyName() + "\n").getBytes());
		out.write(("Currency Balance: " + a.getCurrencyName() + " " + a.getBalance() + "\n").getBytes());
		out.write(("USD Balance: USD " + a.getCurrencyBalance() + "\n").getBytes());

	}

	public static void printAccountTransactions(int accountNumber, OutputStream out)
			throws IOException, NoSuchAccountException {

		lookup(accountNumber).printTransactions(out);
	}

}
