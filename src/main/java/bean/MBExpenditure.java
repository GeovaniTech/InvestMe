package bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import manter.expenditure.ManterExpenditure;
import manter.transaction.ManterTransaction;
import manter.type.ManterType;
import model.Type;
import to.TOTransaction;
import to.TOType;
import utils.AbstractBean;

@Named("MBExpenditure")
@ViewScoped
public class MBExpenditure extends AbstractBean {
	private static final long serialVersionUID = 6177450877846959657L;

	private ManterTransaction manterTransaction;
	private ManterExpenditure manterExpenditure;
	private ManterType manterType;
	private int convertId;
	private TOTransaction toTransaction;
	private List<TOTransaction> expenditures;
	
	public MBExpenditure() {
		this.manterTransaction = new ManterTransaction();
		this.manterExpenditure = new ManterExpenditure();
		this.manterType = new ManterType();
		this.toTransaction = new TOTransaction();
		this.expenditures = new ArrayList<TOTransaction>();
		
		listExpendiures();
	}
	
	public void save() {
		if(toTransaction.getActive() != null
				&& toTransaction.getAmount() != null
				&& toTransaction.getDate() != null
				&& toTransaction.getPrice() != null) {
				
				toTransaction.setTypeActive(convertType());
				toTransaction.setTypeTransaction("Expenditure");
				
				this.getManterTransaction().save(this.getToTransaction());
			
				listExpendiures();
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
		
			listExpendiures();
		}
	}
	
	public void remove(TOTransaction toTransaction) {
		try {
			this.getManterTransaction().remove(toTransaction);
			
			listExpendiures();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listExpendiures() {
		this.setExpenditures(this.getManterTransaction().list("Expenditure"));
	}
	
	public Type convertType() {
		TOType dtoType = this.getManterType().findById(this.getConvertId());
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

	public List<TOTransaction> getExpenditures() {
		return expenditures;
	}

	public void setExpenditures(List<TOTransaction> expenditures) {
		this.expenditures = expenditures;
	}

	public ManterExpenditure getManterExpenditure() {
		return manterExpenditure;
	}

	public void setManterExpenditure(ManterExpenditure manterExpenditure) {
		this.manterExpenditure = manterExpenditure;
	}

	public ManterType getManterType() {
		return manterType;
	}

	public void setManterType(ManterType manterType) {
		this.manterType = manterType;
	}

	public TOTransaction getToTransaction() {
		return toTransaction;
	}

	public void setToTransaction(TOTransaction toTransaction) {
		this.toTransaction = toTransaction;
	}

	public int getConvertId() {
		return convertId;
	}

	public void setConvertId(int convertId) {
		this.convertId = convertId;
	}
}
