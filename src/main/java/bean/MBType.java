package bean;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import manter.type.ManterType;
import to.TOType;
import utils.AbstractBean;

@Named("MBType")
@ViewScoped
public class MBType extends AbstractBean {
	private static final long serialVersionUID = 1230226415644681012L;
	
	private TOType dtoType = new TOType();
	private ManterType daoType = new ManterType();
	private List<TOType> types;
	private List<TOType> typesExpenditure;
	private List<TOType> typesInvesments;
	
	public MBType() {
		updateTypes();
	}
	
	public void save() {
		if(this.getDtoType().getName() != null
				&& !this.getDtoType().getName().equals("")) {
			this.getDaoType().save(this.getDtoType());
			this.setDtoType(new TOType());
			
			updateTypes();
			msg.saveSuccessfully();
		} else {
			msg.emptyValues();
		}
	}
	
	public void remove(TOType to) {
		try {
			this.getDaoType().remove(to);
			
			updateTypes();
			msg.removeSuccessfully();
		} catch (Exception e) {
			msg.emptyValues();
		}
		
	}
	
	public void updateTypes() {
		this.setTypes(this.getDaoType().list());
		this.setTypesExpenditure(this.getDaoType().list("Expenditure"));
		this.setTypesInvesments(this.getDaoType().list("Investment"));
	}
	
	//Getters and Setters
	public ManterType getDaoType() {
		return daoType;
	}
	public void setDaoType(ManterType daoType) {
		this.daoType = daoType;
	}
	public List<TOType> getTypes() {
		return types;
	}
	public void setTypes(List<TOType> types) {
		this.types = types;
	}
	public TOType getDtoType() {
		return dtoType;
	}
	public void setDtoType(TOType dtoType) {
		this.dtoType = dtoType;
	}

	public List<TOType> getTypesExpenditure() {
		return typesExpenditure;
	}

	public void setTypesExpenditure(List<TOType> typesExpenditure) {
		this.typesExpenditure = typesExpenditure;
	}

	public List<TOType> getTypesInvesments() {
		return typesInvesments;
	}

	public void setTypesInvesments(List<TOType> typesInvesments) {
		this.typesInvesments = typesInvesments;
	}
}
