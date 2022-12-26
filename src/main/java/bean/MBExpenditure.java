package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import manter.ManterExpenditure;
import manter.ManterTransaction;
import to.TOTransaction;

@Named("MBExpenditure")
@ViewScoped
public class MBExpenditure implements Serializable {
	private static final long serialVersionUID = 6177450877846959657L;

	private ManterTransaction manterTransaction;
	private ManterExpenditure manterExpenditure;
	private List<TOTransaction> expenditures;
	
	private Double spents;
	private Double recreation;
	private Double market;
	private Double ifood;
	private Double others;
	
	public MBExpenditure() {
		this.manterTransaction = new ManterTransaction();
		this.manterExpenditure = new ManterExpenditure();
		this.expenditures = new ArrayList<TOTransaction>();
		this.spents = Double.valueOf(0);
		this.recreation = Double.valueOf(0);
		this.market = Double.valueOf(0);
		this.ifood = Double.valueOf(0);
		this.others = Double.valueOf(0);
		
		listExpendiures();
		updateDashboard();
	}

	public void listExpendiures() {
		this.setExpenditures(this.getManterTransaction().list("Expenditure"));
	}

	public void updateDashboard() {
		this.setSpents(this.getManterExpenditure().spents());
		this.setRecreation(this.getManterExpenditure().recreation());
		this.setMarket(this.getManterExpenditure().market());
		this.setIfood(this.getManterExpenditure().ifood());
		this.setOthers(this.getManterExpenditure().others());
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

	public ManterExpenditure getManterExpenditure() {
		return manterExpenditure;
	}

	public void setManterExpenditure(ManterExpenditure manterExpenditure) {
		this.manterExpenditure = manterExpenditure;
	}
}
