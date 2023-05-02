import java.io.BufferedReader;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.URI;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
public class test {
	public static void main(String[] args) throws IOException, InterruptedException {
		HttpRequest.Builder builder = HttpRequest.newBuilder();
		// builder.uri(URI.create("http://www.usman.cloud/banking/exchange-rate.csv"));
		builder.uri(URI.create("http://www.usman.cloud/banking/exchange-rate.csv"));

		builder.method("GET", HttpRequest.BodyPublishers.noBody());

		HttpRequest req = builder.build();

		// Step 3

		HttpClient client = HttpClient.newHttpClient();

		// Step 4

		HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
		//System.out.println(response.body());
		
		BufferedReader reader = new BufferedReader (new StringReader(response.body()));
		String line;
//		while ((line = reader.readLine())!=null) {
//			System.out.println(line);
//		}
		
		System.out.println(response.statusCode());
	}

}
