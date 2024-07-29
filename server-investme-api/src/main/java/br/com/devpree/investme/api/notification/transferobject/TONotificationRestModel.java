package br.com.devpree.investme.api.notification.transferobject;

public class TONotificationRestModel {
	private String active;
	private Double actualPrice;
	private Double avaragePrice;
	
	public TONotificationRestModel(String active, Double actualPrice, Double avaragePrice) {
		super();
		this.active = active;
		this.actualPrice = actualPrice;
		this.avaragePrice = avaragePrice;
	}
	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Double getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(Double actualPrice) {
		this.actualPrice = actualPrice;
	}
	public Double getAvaragePrice() {
		return avaragePrice;
	}
	public void setAvaragePrice(Double avaragePrice) {
		this.avaragePrice = avaragePrice;
	}
	
}
