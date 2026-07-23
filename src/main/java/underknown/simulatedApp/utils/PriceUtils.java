package underknown.simulatedApp.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PriceUtils {

	private static PriceUtils priceUtils = null;

	private PriceUtils() {
	}

	public static PriceUtils getInstance() {
		if (priceUtils == null) {
			priceUtils = new PriceUtils();
		}

		return priceUtils;
	}

	public String getIndiceValue(final String indice) throws IOException, InterruptedException, NullPointerException {

			final StringBuilder sb = new StringBuilder("https://query1.finance.yahoo.com/v8/finance/chart/");
			sb.append(indice);
			
			final HttpClient client = HttpClient.newHttpClient();
			final HttpRequest request = HttpRequest.newBuilder()
			        .uri(URI.create(sb.toString()))
			        .header("User-Agent", "Mozilla/5.0")
			        .build();

			final HttpResponse<InputStream> response =
			        client.send(request, HttpResponse.BodyHandlers.ofInputStream());

			final ObjectMapper mapper = new ObjectMapper();
			final JsonNode root = mapper.readTree(response.body());

			final String price = root
			        .path("chart")
			        .path("result")
			        .get(0)
			        .path("meta")
			        .path("regularMarketPrice")
			        .asText();

			return price;

	}
}
