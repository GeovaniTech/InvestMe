package brapi.webservice.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import brapi.transferobject.TOStockBrapiAPI;

public class QuoteListResponse {
	@SerializedName("stocks")
	private List<TOStockBrapiAPI> stocks;

	public List<TOStockBrapiAPI> getStocks() {
		return stocks;
	}

	public void setStocks(List<TOStockBrapiAPI> stocks) {
		this.stocks = stocks;
	}
	
}
