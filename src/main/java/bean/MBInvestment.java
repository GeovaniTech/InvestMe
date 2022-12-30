package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import interfaces.Messages;
import manter.ManterInvestment;
import manter.ManterTransaction;
import manter.ManterType;
import model.Type;
import to.TOTransaction;
import to.TOType;

@Named("MBInvestment")
@ViewScoped
public class MBInvestment implements Serializable, Messages {
	private static final long serialVersionUID = 4252751160550859892L;
	
	private ManterTransaction manterTransaction;
	private ManterInvestment manterInvetment;
	private ManterType manterType;
	private List<TOTransaction> investments;
	private TOTransaction toTransaction;
	
	private Double spents;
	private Double actions;
	private Double fiis;
	private Double fixedIncome;
	private Double criptocurrencys;
	
	private int convertType;
	
	public MBInvestment() {
		this.manterTransaction = new ManterTransaction();
		this.manterInvetment = new ManterInvestment();
		this.manterType = new ManterType();
		this.investments = new ArrayList<TOTransaction>();
		this.toTransaction = new TOTransaction();
		this.spents = Double.valueOf(0);
		this.actions = Double.valueOf(0);
		this.fiis = Double.valueOf(0);
		this.fixedIncome = Double.valueOf(0);
		this.criptocurrencys = Double.valueOf(0);
		
		
		listInvestments();
		updateDashboard();
	}
	
	public void save() {
		if(toTransaction.getActive() != null
			&& toTransaction.getAmount() != null
			&& toTransaction.getDate() != null
			&& toTransaction.getPrice() != null
			&& toTransaction.getTypeTransaction() != null) {
			
			toTransaction.setTypeActive(convertType());
			
			this.getManterTransaction().save(this.getToTransaction());
			
			listInvestments();
			updateDashboard();
			
			PrimeFaces.current().ajax().update(":tableInvestments");
			msg.saveSuccessfully();
		} else {
			msg.emptyValues();
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
			
			updateDashboard();
			listInvestments();
			
			PrimeFaces.current().ajax().update(":tableInvestments");
			msg.changedSuccessfully();
		}
	}
	
	public void remove(TOTransaction toTransaction) {
		try {
			this.getManterTransaction().remove(toTransaction);
			
			updateDashboard();
			listInvestments();
			
			msg.saveSuccessfully();
		} catch (Exception e) {
			msg.errorRemoving();
			e.printStackTrace();
		}
	}
	
	public void listInvestments() {
		this.setInvestments(this.getManterTransaction().list("Investment"));
	}

	public void updateDashboard() {
		this.setSpents(this.getManterInvetment().spents());
		this.setActions(this.getManterInvetment().actions());
		this.setFiis(this.getManterInvetment().fiis());
		this.setFixedIncome(this.getManterInvetment().fixedIncome());
		this.setCriptocurrencys(this.getManterInvetment().criptocurrencys());
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

	public Double getSpents() {
		return spents;
	}

	public void setSpents(Double spents) {
		this.spents = spents;
	}

	public Double getActions() {
		return actions;
	}

	public void setActions(Double actions) {
		this.actions = actions;
	}

	public Double getFiis() {
		return fiis;
	}

	public void setFiis(Double fiis) {
		this.fiis = fiis;
	}

	public Double getFixedIncome() {
		return fixedIncome;
	}

	public void setFixedIncome(Double fixedIncome) {
		this.fixedIncome = fixedIncome;
	}

	public Double getCriptocurrencys() {
		return criptocurrencys;
	}

	public void setCriptocurrencys(Double criptocurrencys) {
		this.criptocurrencys = criptocurrencys;
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
