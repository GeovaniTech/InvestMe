package br.com.devpree.investme.webservice.brapi.transferobject;

import com.google.gson.annotations.SerializedName;

public class TOTickerBrapiAPI {
	@SerializedName("symbol")
	private String symbol;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("regularMarketPrice")
	private Double currentPrice;
	
	@SerializedName("logo")
	private String logo;
	
	@SerializedName("type")
	private String type;
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}
}
