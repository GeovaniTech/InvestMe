package model;

import abstracts.AbstractObject;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Payment extends AbstractObject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private boolean installmentable;
	private Integer dueDate;
	private Integer closeDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isInstallmentable() {
		return installmentable;
	}
	public void setInstallmentable(boolean installmentable) {
		this.installmentable = installmentable;
	}
	public Integer getDueDate() {
		return dueDate;
	}
	public void setDueDate(Integer dueDate) {
		this.dueDate = dueDate;
	}
	public Integer getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Integer closeDate) {
		this.closeDate = closeDate;
	}
}
