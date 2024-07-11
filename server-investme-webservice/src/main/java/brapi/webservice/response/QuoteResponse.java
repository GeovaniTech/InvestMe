package brapi.webservice.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import brapi.transferobject.TOTickerBrapiAPI;

public class QuoteResponse {
	@SerializedName("results")
	private List<TOTickerBrapiAPI> results;

	public List<TOTickerBrapiAPI> getResults() {
		return results;
	}

	public void setResults(List<TOTickerBrapiAPI> results) {
		this.results = results;
	}
	
}
