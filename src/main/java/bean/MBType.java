package bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import dao.DAOType;
import dto.DTOType;

@Named("MBType")
@ViewScoped
public class MBType implements Serializable {
	private static final long serialVersionUID = 1230226415644681012L;
	
	private DTOType dtoType = new DTOType();
	private DAOType daoType = new DAOType();
	private List<DTOType> types;
	
	public MBType() {
		updateTypes();
	}
	
	public void save() {
		if(!this.getDtoType().getName().equals("")) {
			this.getDaoType().save(this.getDtoType());
			this.setDtoType(new DTOType());
			
			updateTypes();
		} else {
			
		}
	}
	
	public void remove(DTOType to) {
		this.getDaoType().remove(to);
		updateTypes();
	}
	
	public void updateTypes() {
		this.setTypes(this.getDaoType().list());
	}
	
	//Getters and Setters
	public DAOType getDaoType() {
		return daoType;
	}
	public void setDaoType(DAOType daoType) {
		this.daoType = daoType;
	}
	public List<DTOType> getTypes() {
		return types;
	}
	public void setTypes(List<DTOType> types) {
		this.types = types;
	}
	public DTOType getDtoType() {
		return dtoType;
	}
	public void setDtoType(DTOType dtoType) {
		this.dtoType = dtoType;
	}
}
