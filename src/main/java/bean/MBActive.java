package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import dao.DAOActive;
import dao.DAOType;
import dto.DTOActive;
import dto.DTOType;
import interfaces.Messages;
import model.Type;

@Named("MBActive")
@ViewScoped
public class MBActive implements Serializable, Messages {
	private static final long serialVersionUID = 3027991206428134167L;

	private DTOActive dtoActive = new DTOActive();
	private DAOActive daoActive = new DAOActive();
	private DAOType daoType = new DAOType();
	private List<DTOActive> actives = new ArrayList<DTOActive>();
	
	// ID to convert DTOtype to model
	private int idType = 0;
	
	public MBActive() {
		updateActives();
	}
	
	public void save() {
		if(this.getDtoActive().getName() != null
			&& this.getDtoActive().getPrice() > 0
			&& this.getIdType() != 0) {
			
			this.getDtoActive().setType(convertTypeId(this.getIdType()));
			
			this.getDaoActive().save(this.getDtoActive());
			this.setDtoActive(new DTOActive());
			
			updateActives();
			msg.saveSuccessfully();
		} else  {
			msg.emptyValues();
		}
	}
	
	public void remove(DTOActive dtoActive) {
		try {
			this.getDaoActive().remove(dtoActive);
			
			updateActives();
			msg.removeSuccessfully();
		} catch (Exception e) {
			msg.errorRemoving();
		}
	}
	
	public void updateActives() {
		this.setActives(this.getDaoActive().list());
	}
	
	public Type convertTypeId(int id) {
		DTOType dtoType = daoType.findById(id);
		Type type = new Type();
		
		type.setId(dtoType.getId());
		type.setName(dtoType.getName());
		
		return type;
	}

	//Getters and Setters
	public DTOActive getDtoActive() {
		return dtoActive;
	}

	public void setDtoActive(DTOActive dtoActive) {
		this.dtoActive = dtoActive;
	}

	public DAOActive getDaoActive() {
		return daoActive;
	}

	public void setDaoActive(DAOActive daoActive) {
		this.daoActive = daoActive;
	}

	public List<DTOActive> getActives() {
		return actives;
	}

	public void setActives(List<DTOActive> actives) {
		this.actives = actives;
	}
	
	public DAOType getDaoType() {
		return daoType;
	}
	
	public void setDaoType(DAOType daoType) {
		this.daoType = daoType;
	}

	public int getIdType() {
		return idType;
	}

	public void setIdType(int idType) {
		this.idType = idType;
	}
}
