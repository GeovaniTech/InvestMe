package br.com.devpree.investme.api.transaction.transferobject;

import java.util.Date;

import br.com.devpree.investme.api.common.AbstractTOObject;
import to.category.TOCategory;
import to.payment.TOPayment;

public class TOTransactionRestModel extends AbstractTOObject {

	private static final long serialVersionUID = -5874313861913707910L;

	private String active;
	private Double price;
	private TOCategory category;
	private TOPayment payment;
	private Date purchaseDate;
	private Integer amount;
	
	// Getters and Setters
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
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
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

}
