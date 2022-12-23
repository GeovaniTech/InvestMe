package model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String active;
	private Double price;
	private Integer amount;
	private Date date;
	private String typeTransaction;
	@ManyToOne
	private Type typeActive;
	
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Type getTypeActive() {
		return typeActive;
	}
	public void setTypeActive(Type typeActive) {
		this.typeActive = typeActive;
	}
	public String getTypeTransaction() {
		return typeTransaction;
	}
	public void setTypeTransaction(String typeTransaction) {
		this.typeTransaction = typeTransaction;
	}
}
