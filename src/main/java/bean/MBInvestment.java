package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import dao.DAOActive;
import dao.DAOInvestment;
import dto.DTOActive;
import dto.DTOInvestment;
import interfaces.Messages;
import model.Active;

@Named("MBInvestment")
@ViewScoped
public class MBInvestment implements Serializable, Messages {
	private static final long serialVersionUID = 4252751160550859892L;
	
	private DAOActive daoActive = new DAOActive();
	private DTOInvestment dtoInvestment = new DTOInvestment();
	private DAOInvestment daoInvestment = new DAOInvestment();
	private List<DTOInvestment> investments = new ArrayList<DTOInvestment>();
	
	// ID to convert DTOActve to model
	private int idActive = 0;
	
	public MBInvestment() {
		updateInvestments();
	}
	
	public void save() {
		if(this.getDtoInvestment().getAmount() > 0
			&& this.getDtoInvestment().getDate() != null
			&& this.getIdActive() > 0) {
			
			this.getDtoInvestment().setActive(convertActive());
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
	}
	
	public Active convertActive() {
		DTOActive  dtoActive = daoActive.findById(this.getIdActive());
		Active active = new Active();
		
		active.setId(dtoActive.getId());
		active.setName(dtoActive.getName());
		active.setPrice(dtoActive.getPrice());
		active.setType(dtoActive.getType());
		
		return active;
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

	public int getIdActive() {
		return idActive;
	}

	public void setIdActive(int idActive) {
		this.idActive = idActive;
	}

	public DAOActive getDaoActive() {
		return daoActive;
	}

	public void setDaoActive(DAOActive daoActive) {
		this.daoActive = daoActive;
	}
}
