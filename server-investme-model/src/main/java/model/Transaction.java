package model;

import java.util.Date;

import abstracts.AbstractObject;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction extends AbstractObject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String active;
	private Double price;
	private Integer amount;
	private Date datePurchase;
	private boolean paid;
	
	@ManyToOne
	private Client client;
	
	@ManyToOne
	private Category category;

	@ManyToOne
	private Payment payment;
	
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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

}
