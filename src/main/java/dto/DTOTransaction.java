package dto;

import java.io.Serializable;
import java.util.Date;

import model.Type;

public class DTOTransaction implements Serializable {
	private static final long serialVersionUID = -8168060083920299146L;
	
	private int id;
	private String active;
	private Double price;
	private Integer amount;
	private Date date;
	private String typeTrasanction;
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
	public String getTypeTrasanction() {
		return typeTrasanction;
	}
	public void setTypeTrasanction(String typeTrasanction) {
		this.typeTrasanction = typeTrasanction;
	}
	public Type getTypeActive() {
		return typeActive;
	}
	public void setTypeActive(Type typeActive) {
		this.typeActive = typeActive;
	}
}
