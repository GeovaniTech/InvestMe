package br.com.devpree.investme.webservice.brapi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.com.devpree.investme.webservice.abstracts.AbstractWebService;
import br.com.devpree.investme.webservice.brapi.response.QuoteListResponse;
import br.com.devpree.investme.webservice.brapi.response.QuoteResponse;
import br.com.devpree.investme.webservice.brapi.transferobject.TOStockBrapiAPI;
import br.com.devpree.investme.webservice.brapi.transferobject.TOTickerBrapiAPI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import security.wildfly.WildflyConfigs;

@ApplicationScoped
@Transactional
public class BrapiWebService extends AbstractWebService {
	
	private static final long serialVersionUID = -2849136246032960800L;
	
	private final String BASE_URL = "https://brapi.dev/api/";
	
	@ConfigProperty(name = "brapi_token")
	private String AUTH_TOKEN;
	
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
