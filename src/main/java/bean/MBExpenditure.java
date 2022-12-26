package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import manter.ManterTransaction;
import to.TOTransaction;

@Named("MBExpenditure")
@ViewScoped
public class MBExpenditure implements Serializable {
	private static final long serialVersionUID = 6177450877846959657L;

	private ManterTransaction manterTransaction;
	private List<TOTransaction> expenditures;
	
	private Double spents;
	private Double recreation;
	private Double market;
	private Double ifood;
	private Double others;
	
	public MBExpenditure() {
		this.manterTransaction = new ManterTransaction();
		this.expenditures = new ArrayList<TOTransaction>();
		this.spents = Double.valueOf(0);
		this.recreation = Double.valueOf(0);
		this.market = Double.valueOf(0);
		this.ifood = Double.valueOf(0);
		this.others = Double.valueOf(0);
		
		listExpendiures();
	}
	
	public void listExpendiures() {
		this.setExpenditures(this.getManterTransaction().list("Expenditure"));
	}

	public ManterTransaction getManterTransaction() {
		return manterTransaction;
	}

	public void setManterTransaction(ManterTransaction manterTransaction) {
		this.manterTransaction = manterTransaction;
	}

	public List<TOTransaction> getExpenditures() {
		return expenditures;
	}

	public void setExpenditures(List<TOTransaction> expenditures) {
		this.expenditures = expenditures;
	}

	public Double getSpents() {
		return spents;
	}

	public void setSpents(Double spents) {
		this.spents = spents;
	}

	public Double getRecreation() {
		return recreation;
	}

	public void setRecreation(Double recreation) {
		this.recreation = recreation;
	}

	public Double getMarket() {
		return market;
	}

	public void setMarket(Double market) {
		this.market = market;
	}

	public Double getIfood() {
		return ifood;
	}

	public void setIfood(Double ifood) {
		this.ifood = ifood;
	}

	public Double getOthers() {
		return others;
	}

	public void setOthers(Double others) {
		this.others = others;
	}
}
