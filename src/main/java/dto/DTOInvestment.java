package dto;

import java.util.Date;

import model.Active;

public class DTOInvestment {
	private int id;
	private Active active;
	private float amount;
	private Date date;
	
	//Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Active getActive() {
		return active;
	}
	public void setActive(Active active) {
		this.active = active;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
