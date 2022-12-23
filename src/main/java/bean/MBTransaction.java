package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import dao.DAOTransaction;
import dao.DAOType;
import dto.DTOTransaction;
import dto.DTOType;
import interfaces.Messages;
import model.Type;

@Named("MBTransaction")
@ViewScoped
public class MBTransaction implements Serializable, Messages {
	private static final long serialVersionUID = 2380786016163770521L;
	
	private DAOType daoType;
	private DAOTransaction daoTransaction;
	private DTOTransaction dtoTransacation;
	private Integer convertType;
	private List<DTOTransaction> transactions;
	
	public MBTransaction() {
		this.daoTransaction = new DAOTransaction();
		this.dtoTransacation = new DTOTransaction();
		this.daoTransaction = new DAOTransaction();
		this.convertType = null;
		this.transactions = new ArrayList<DTOTransaction>();
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
		DTOType dtoType = this.getDaoType().findById(this.getConvertType());
		Type type = new Type();
		
		type.setId(dtoType.getId());
		type.setName(dtoType.getName());
		
		return type;
	}

	public DAOTransaction getDaoTransaction() {
		return daoTransaction;
	}

	public void setDaoTransaction(DAOTransaction daoTransaction) {
		this.daoTransaction = daoTransaction;
	}

	public DTOTransaction getDtoTransacation() {
		return dtoTransacation;
	}

	public void setDtoTransacation(DTOTransaction dtoTransacation) {
		this.dtoTransacation = dtoTransacation;
	}

	public Integer getConvertType() {
		return convertType;
	}

	public void setConvertType(Integer convertType) {
		this.convertType = convertType;
	}

	public DAOType getDaoType() {
		return daoType;
	}

	public void setDaoType(DAOType daoType) {
		this.daoType = daoType;
	}

	public List<DTOTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<DTOTransaction> transactions) {
		this.transactions = transactions;
	}
}
