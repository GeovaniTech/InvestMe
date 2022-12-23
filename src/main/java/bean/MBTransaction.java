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
	
	private ManterType daoType;
	private ManterTransaction daoTransaction;
	private TOTransaction dtoTransacation;
	private Integer convertType;
	private List<TOTransaction> transactions;
	
	public MBTransaction() {
		this.daoTransaction = new ManterTransaction();
		this.dtoTransacation = new TOTransaction();
		this.daoTransaction = new ManterTransaction();
		this.convertType = null;
		this.transactions = new ArrayList<TOTransaction>();
		listTransactions();
	}
	
	public void save() {
		if(this.getDtoTransacation().getActive() != null
			&& this.getDtoTransacation().getPrice() != null
			&& this.getDtoTransacation().getAmount() != null
			&& this.getDtoTransacation().getTypeTrasanction() != null
			&& this.getDtoTransacation().getDate() != null) {
			
			this.getDtoTransacation().setTypeActive(this.convertType());
			
			this.getDaoTransaction().save(this.getDtoTransacation());
			listTransactions();
			msg.saveSuccessfully();
		} else {
			msg.emptyValues();
		}
	}
	
	public void change() {
		
	}
	
	public void remove() {
		this.getDaoTransaction().remove(this.getDtoTransacation());
		listTransactions();
	}
	
	public void listTransactions() {
		this.setTransactions(this.getDaoTransaction().list());
	}
	
	public Type convertType() {
		TOType dtoType = this.getDaoType().findById(this.getConvertType());
		Type type = new Type();
		
		type.setId(dtoType.getId());
		type.setName(dtoType.getName());
		
		return type;
	}

	public ManterTransaction getDaoTransaction() {
		return daoTransaction;
	}

	public void setDaoTransaction(ManterTransaction daoTransaction) {
		this.daoTransaction = daoTransaction;
	}

	public TOTransaction getDtoTransacation() {
		return dtoTransacation;
	}

	public void setDtoTransacation(TOTransaction dtoTransacation) {
		this.dtoTransacation = dtoTransacation;
	}

	public Integer getConvertType() {
		return convertType;
	}

	public void setConvertType(Integer convertType) {
		this.convertType = convertType;
	}

	public ManterType getDaoType() {
		return daoType;
	}

	public void setDaoType(ManterType daoType) {
		this.daoType = daoType;
	}

	public List<TOTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TOTransaction> transactions) {
		this.transactions = transactions;
	}
}
