package brapi.webservice;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import abstracts.AbstractWebService;
import brapi.transferobject.TOStockBrapiAPI;
import brapi.transferobject.TOTickerBrapiAPI;
import brapi.webservice.response.QuoteListResponse;
import brapi.webservice.response.QuoteResponse;
import security.wildfly.WildflyConfigs;

public class BrapiWebService extends AbstractWebService {
	private final String BASE_URL = "https://brapi.dev/api/";
	private final String AUTH_TOKEN = WildflyConfigs.getConfig("brapi_token");
	
	public TOTickerBrapiAPI getTicker(String symbol) {
		String url = BASE_URL + "quote/" + symbol + "?token=" + AUTH_TOKEN;
		
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        TOTickerBrapiAPI ticker = new TOTickerBrapiAPI();
        
        try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			QuoteResponse quote = getGson().fromJson(response.body(), QuoteResponse.class);
			ticker = quote.getResults().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return ticker;
	}
	
	public List<TOStockBrapiAPI> getAllStocks() {
		String url = BASE_URL + "quote/list" + "?token=" + AUTH_TOKEN;
		
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        
    	QuoteListResponse quoteList = new QuoteListResponse();
        
        try {
        	HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        	quoteList = getGson().fromJson(response.body(), QuoteListResponse.class);
        } catch (Exception e) {
			e.printStackTrace();
		}
        
        return quoteList.getStocks();
	}
}
