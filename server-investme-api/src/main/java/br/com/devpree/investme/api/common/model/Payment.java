package br.com.devpree.investme.api.common.model;

import br.com.devpree.investme.api.common.AbstractObject;
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
}
