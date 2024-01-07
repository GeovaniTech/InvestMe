package to.transaction;

import java.util.Date;

import abstracts.AbstractTOObject;
import to.category.TOCategory;
import to.client.TOClient;
import to.payment.TOPayment;

public class TOTransaction extends AbstractTOObject {

	private static final long serialVersionUID = -1384422120365897196L;
	
	private int id;
	private String active;
	private Double price;
	private Integer amount;
	private Date datePurchase;
	private TOCategory category;
	private TOPayment payment;
	private TOClient client;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Date getDatePurchase() {
		return datePurchase;
	}
	public void setDatePurchase(Date datePurchase) {
		this.datePurchase = datePurchase;
	}
	public TOCategory getCategory() {
		return category;
	}
	public void setCategory(TOCategory category) {
		this.category = category;
	}
	public TOPayment getPayment() {
		return payment;
	}
	public void setPayment(TOPayment payment) {
		this.payment = payment;
	}
	public TOClient getClient() {
		return client;
	}
	public void setClient(TOClient client) {
		this.client = client;
	}
	
}
