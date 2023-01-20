package manter.type;

import javax.ejb.Local;

import interfaces.GenericDAO;
import to.TOType;

@Local
public interface IManterTypeSBean extends GenericDAO<TOType> {
	public Double totalSpents(String typeName);
}
