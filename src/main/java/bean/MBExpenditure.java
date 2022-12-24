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
	
	public MBExpenditure() {
		this.manterTransaction = new ManterTransaction();
		this.expenditures = new ArrayList<TOTransaction>();
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
}
