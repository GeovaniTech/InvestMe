package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import manter.ManterTransaction;
import to.TOTransaction;

@Named("MBInvestment")
@ViewScoped
public class MBInvestment implements Serializable {
	private static final long serialVersionUID = 4252751160550859892L;
	
	private ManterTransaction manterTransaction;
	private List<TOTransaction> investments;
	
	private Double spents;
	private Double actions;
	private Double fiis;
	private Double fixedIncome;
	private Double criptocurrencys;
	
	public MBInvestment() {
		this.manterTransaction = new ManterTransaction();
		this.investments = new ArrayList<TOTransaction>();
		this.spents = Double.valueOf(0);
		this.actions = Double.valueOf(0);
		this.fiis = Double.valueOf(0);
		this.fixedIncome = Double.valueOf(0);
		this.criptocurrencys = Double.valueOf(0);
		
		listInvestments();
	}
	
	public void listInvestments() {
		this.setInvestments(this.getManterTransaction().list("Investment"));
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
}
