package brapi.transferobject;

import com.google.gson.annotations.SerializedName;

public class TOStockBrapiAPI {
	@SerializedName("stock")
	private String active;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("close")
	private Double price;
	
	@SerializedName("logo")
	private String logo;
	
	@SerializedName("sector")
	private String sector;
	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
}
