package bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import manter.ManterExpenditure;
import manter.ManterTransaction;
import manter.ManterType;
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
	
	private Double spents;
	private Double recreation;
	private Double market;
	private Double ifood;
	private Double others;
	
	public MBExpenditure() {
		this.manterTransaction = new ManterTransaction();
		this.manterExpenditure = new ManterExpenditure();
		this.manterType = new ManterType();
		this.toTransaction = new TOTransaction();
		this.expenditures = new ArrayList<TOTransaction>();
		this.spents = Double.valueOf(0);
		this.recreation = Double.valueOf(0);
		this.market = Double.valueOf(0);
		this.ifood = Double.valueOf(0);
		this.others = Double.valueOf(0);
		
		listExpendiures();
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
				
				updateDashboard();
				listExpendiures();
				
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
			listExpendiures();
			
			msg.changedSuccessfully();
		}
	}
	
	public void remove(TOTransaction toTransaction) {
		try {
			this.getManterTransaction().remove(toTransaction);
			
			updateDashboard();
			listExpendiures();
			
			msg.saveSuccessfully();
		} catch (Exception e) {
			msg.errorRemoving();
			e.printStackTrace();
		}
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
