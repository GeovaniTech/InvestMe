package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import interfaces.Messages;
import manter.ManterTransaction;
import manter.ManterType;
import model.Type;
import to.TOTransaction;
import to.TOType;

@Named("MBTransaction")
@ViewScoped
public class MBTransaction implements Serializable, Messages {
	private static final long serialVersionUID = 2380786016163770521L;
	
	private ManterType manterType;
	private ManterTransaction manterTransaction;
	private TOTransaction toTransaction;
	private Integer convertType;
	private List<TOTransaction> transactions;
	
	public MBTransaction() {
		this.manterTransaction = new ManterTransaction();
		this.toTransaction = new TOTransaction();
		this.manterType = new ManterType();
		this.convertType = null;
		this.transactions = new ArrayList<TOTransaction>();
		listTransactions();
	}
	
	public void save() {
		if(this.getToTransaction().getActive() != null
			&& this.getToTransaction().getPrice() != null
			&& this.getToTransaction().getAmount() != null
			&& this.getToTransaction().getTypeTrasanction() != null
			&& this.getToTransaction().getDate() != null) {
			
			this.getToTransaction().setTypeActive(this.convertType());
			
			this.getManterTransaction().save(this.getToTransaction());
			listTransactions();
			msg.saveSuccessfully();
		} else {
			msg.emptyValues();
		}
	}
	
	public void change() {
		
	}
	
	public void remove() {
		this.getManterTransaction().remove(this.getToTransaction());
		listTransactions();
	}
	
	public void listTransactions() {
		this.setTransactions(this.getManterTransaction().list());
	}
	
	public Type convertType() {
		TOType dtoType = this.getManterType().findById(this.getConvertType());
		Type type = new Type();
		
		type.setId(dtoType.getId());
		type.setName(dtoType.getName());
		
		return type;
	}

	public ManterType getManterType() {
		return manterType;
	}

	public void setManterType(ManterType manterType) {
		this.manterType = manterType;
	}

	public ManterTransaction getManterTransaction() {
		return manterTransaction;
	}

	public void setManterTransaction(ManterTransaction manterTransaction) {
		this.manterTransaction = manterTransaction;
	}

	public TOTransaction getToTransaction() {
		return toTransaction;
	}

	public void setToTransaction(TOTransaction toTransaction) {
		this.toTransaction = toTransaction;
	}

	public Integer getConvertType() {
		return convertType;
	}

	public void setConvertType(Integer convertType) {
		this.convertType = convertType;
	}

	public List<TOTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TOTransaction> transactions) {
		this.transactions = transactions;
	}
}
