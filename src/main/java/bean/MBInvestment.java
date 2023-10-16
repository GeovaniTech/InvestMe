package bean;

import java.util.ArrayList;
import java.util.List;


import jakarta.inject.Named;

import org.primefaces.PrimeFaces;

import jakarta.faces.view.ViewScoped;
import manter.investment.ManterInvestment;
import manter.transaction.ManterTransaction;
import manter.type.ManterType;
import model.Type;
import to.TOTransaction;
import to.TOType;
import utils.AbstractBean;

@Named("MBInvestment")
@ViewScoped
public class MBInvestment extends AbstractBean {
	private static final long serialVersionUID = 4252751160550859892L;
	
	private ManterTransaction manterTransaction;
	private ManterInvestment manterInvetment;
	private ManterType manterType;
	private List<TOTransaction> investments;
	private TOTransaction toTransaction;
	
	private int convertType;
	
	public MBInvestment() {
		this.manterTransaction = new ManterTransaction();
		this.manterInvetment = new ManterInvestment();
		this.manterType = new ManterType();
		this.investments = new ArrayList<TOTransaction>();
		this.toTransaction = new TOTransaction();

		listInvestments();
	}
	
	public void save() {
		if(toTransaction.getActive() != null
			&& toTransaction.getAmount() != null
			&& toTransaction.getDate() != null
			&& toTransaction.getPrice() != null) {
			
			toTransaction.setTypeActive(convertType());
			toTransaction.setTypeTransaction("Investment");
			
			this.getManterTransaction().save(this.getToTransaction());
			
			listInvestments();
			
			PrimeFaces.current().ajax().update(":tableInvestments");
			
		} else {
			
		}
	}
	
	public void change() {
		if(toTransaction.getActive() != null
				&& toTransaction.getAmount() != null
				&& toTransaction.getDate() != null
				&& toTransaction.getPrice() != null
				&& toTransaction.getTypeTransaction() != null) {
			
			toTransaction.setTypeActive(convertType());
			
			this.getManterTransaction().change(this.getToTransaction());
			
			listInvestments();
			
			PrimeFaces.current().ajax().update(":tableInvestments");
	
		}
	}
	
	public void remove(TOTransaction toTransaction) {
		try {
			this.getManterTransaction().remove(toTransaction);
			
			listInvestments();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listInvestments() {
		this.setInvestments(this.getManterTransaction().list("Investment"));
	}
	
	public Type convertType() {
		TOType dtoType = this.getManterType().findById(this.getConvertType());
		Type type = new Type();
		
		type.setId(dtoType.getId());
		type.setName(dtoType.getName());
		
		return type;
	}
	
	public ManterTransaction getManterTransaction() {
		return manterTransaction;
	}

	public void setManterTransaction(ManterTransaction manterTransaction) {
		this.manterTransaction = manterTransaction;
	}

	public List<TOTransaction> getInvestments() {
		return investments;
	}

	public void setInvestments(List<TOTransaction> investments) {
		this.investments = investments;
	}

	public ManterInvestment getManterInvetment() {
		return manterInvetment;
	}

	public void setManterInvetment(ManterInvestment manterInvetment) {
		this.manterInvetment = manterInvetment;
	}

	public TOTransaction getToTransaction() {
		return toTransaction;
	}

	public void setToTransaction(TOTransaction toTransaction) {
		this.toTransaction = toTransaction;
	}

	public ManterType getManterType() {
		return manterType;
	}

	public void setManterType(ManterType manterType) {
		this.manterType = manterType;
	}

	public int getConvertType() {
		return convertType;
	}

	public void setConvertType(int convertType) {
		this.convertType = convertType;
	}
}
