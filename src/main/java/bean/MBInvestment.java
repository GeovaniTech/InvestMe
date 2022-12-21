package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import dao.DAOInvestment;
import dao.DAOType;
import dto.DTOInvestment;
import dto.DTOType;
import interfaces.Messages;
import model.Type;

@Named("MBInvestment")
@ViewScoped
public class MBInvestment implements Serializable, Messages {
	private static final long serialVersionUID = 4252751160550859892L;
	
	private DAOType daoType = new DAOType();
	private DTOInvestment dtoInvestment = new DTOInvestment();
	private DAOInvestment daoInvestment = new DAOInvestment();
	private List<DTOInvestment> investments = new ArrayList<DTOInvestment>();
	
	private Double totalSpent;
	private Double totalActions;
	private Double totalFiis;
	private Double totalFixedIncome;
	private Double totalCriptocurrencys;
	
	// ID to convert DTOActve to model
	private int idType = 0;
	
	public MBInvestment() {		
		updateInvestments();
	}
	
	public void save() {
		if(this.getDtoInvestment().getAmount() > 0
			&& this.getDtoInvestment().getDate() != null
			&& this.getIdType() > 0
			&& this.getDtoInvestment().getActive() != null
			&& this.getDtoInvestment().getActualPrice() > 0) {
			
			this.getDtoInvestment().setType(this.convertType());
			
			this.getDaoInvestment().save(this.getDtoInvestment());
			
			updateInvestments();
			msg.saveSuccessfully();
		} else {
			msg.emptyValues();
		}
	}
	
	public void remove(DTOInvestment to) {
		try {
			this.getDaoInvestment().remove(to);
			
			updateInvestments();
			msg.removeSuccessfully();
		} catch (Exception e) {
			msg.errorRemoving();
		}
	}
	
	public void updateInvestments() {
		this.setInvestments(this.getDaoInvestment().list());
		updateDashboard();
	}
	
	public Type convertType() {
		DTOType dtoType = this.getDaoType().findById(this.getIdType());
		
		Type type = new Type();
		type.setId(dtoType.getId());
		type.setName(dtoType.getName());
		
		return type;
	}
	
	public void updateDashboard() {
		this.updateActions();
		this.updateFiis();
		this.updateFixedIncome();
		this.updateCriptocurrencys();
		this.updateTotalSpent();
	}
	
	public void updateActions() {
		this.setTotalActions(this.getDaoInvestment().totalAction());
	}
	
	public void updateFiis() {
		this.setTotalFiis(this.getDaoInvestment().totalFii());
	}
	
	public void updateFixedIncome() {
		this.setTotalFixedIncome(this.getDaoInvestment().totalFixedIncome());
	}
	
	public void updateCriptocurrencys() {
		this.setTotalCriptocurrencys(this.getDaoInvestment().totalCriptocurrency());
	}
	
	public void updateTotalSpent() {
		this.setTotalSpent(this.getDaoInvestment().totalSpent());
	}
	
	//Getters and Setters
	public DTOInvestment getDtoInvestment() {
		return dtoInvestment;
	}

	public void setDtoInvestment(DTOInvestment dtoInvestment) {
		this.dtoInvestment = dtoInvestment;
	}

	public DAOInvestment getDaoInvestment() {
		return daoInvestment;
	}

	public void setDaoInvestment(DAOInvestment daoInvestment) {
		this.daoInvestment = daoInvestment;
	}

	public List<DTOInvestment> getInvestments() {
		return investments;
	}

	public void setInvestments(List<DTOInvestment> investments) {
		this.investments = investments;
	}

	public int getIdType() {
		return idType;
	}

	public void setIdType(int idType) {
		this.idType = idType;
	}
	
	public DAOType getDaoType() {
		return daoType;
	}
	
	public void setDaoType(DAOType daoType) {
		this.daoType = daoType;
	}

	public Double getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(Double totalSpent) {
		this.totalSpent = totalSpent;
	}

	public Double getTotalActions() {
		return totalActions;
	}

	public void setTotalActions(Double totalActions) {
		this.totalActions = totalActions;
	}

	public Double getTotalFiis() {
		return totalFiis;
	}

	public void setTotalFiis(Double totalFiis) {
		this.totalFiis = totalFiis;
	}

	public Double getTotalFixedIncome() {
		return totalFixedIncome;
	}

	public void setTotalFixedIncome(Double totalFixedIncome) {
		this.totalFixedIncome = totalFixedIncome;
	}

	public Double getTotalCriptocurrencys() {
		return totalCriptocurrencys;
	}

	public void setTotalCriptocurrencys(Double totalCriptocurrencys) {
		this.totalCriptocurrencys = totalCriptocurrencys;
	}
}
