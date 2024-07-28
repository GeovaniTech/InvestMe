package br.com.devpree.investme.webservice.brapi.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import br.com.devpree.investme.webservice.brapi.transferobject.TOStockBrapiAPI;

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
