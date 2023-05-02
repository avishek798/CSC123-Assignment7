package com.usman.csudh.bank.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class WebCurrency extends CurrencyReader {

	@Override
	public String reader(String fileSource) throws FileNotFoundException, IOException, InterruptedException {

		HttpRequest.Builder builder = HttpRequest.newBuilder();

		builder.uri(URI.create(fileSource));

		builder.method("GET", HttpRequest.BodyPublishers.noBody());

		HttpRequest req = builder.build();

		HttpClient client = HttpClient.newHttpClient();

		HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode()!=200) {
			throw new IOException("** Currency could not be loaded from the internet, "
					+ "Currency Conversion Service and Foreign Currency accounts are not available **%n");
		}

		return response.body();
	}

}
