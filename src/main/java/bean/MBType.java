package bean;

import java.util.List;

import javax.inject.Named;

import dao.DAOType;
import model.Type;

@Named
public class MBType {
	private Type type;
	private DAOType daoType = new DAOType();
	private List<Type> types;
	
	public MBType() {
		updateTypes();
	}
	
	public void updateTypes() {
		this.setTypes(this.getDaoType().listTypes());
	}
	
	//Getters and Setters
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public DAOType getDaoType() {
		return daoType;
	}
	public void setDaoType(DAOType daoType) {
		this.daoType = daoType;
	}
	public List<Type> getTypes() {
		return types;
	}
	public void setTypes(List<Type> types) {
		this.types = types;
	}
}
