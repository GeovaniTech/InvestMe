package manter.transaction;

import javax.ejb.Local;

import interfaces.GenericDAO;
import to.TOTransaction;

@Local
public interface IManterTransactionSBean extends GenericDAO<TOTransaction> {
	
}
