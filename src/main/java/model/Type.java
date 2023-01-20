package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Type {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String nameClient;
	private String typeTransaction;
	private Double spents;
	
	//Getters and Setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNameClient() {
		return nameClient;
	}
	public void setNameClient(String nameClient) {
		this.nameClient = nameClient;
	}
	public Double getSpents() {
		return spents;
	}
	public void setSpents(Double spents) {
		this.spents = spents;
	}
	public String getTypeTransaction() {
		return typeTransaction;
	}
	public void setTypeTransaction(String typeTransaction) {
		this.typeTransaction = typeTransaction;
	}
}
