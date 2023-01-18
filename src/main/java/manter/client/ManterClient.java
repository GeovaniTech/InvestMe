package manter.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import interfaces.GenericDAO;
import model.Client;
import to.TOClient;
import utils.AbstractManter;
import utils.PasswordEncryption;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterClient extends AbstractManter implements GenericDAO<TOClient>, IManterClientSBean, IManterClientSbeanRemote {
	
	@Override
	public boolean validateAccess(TOClient toUser, String password) {
		try {
			Client user = (Client) em.createQuery(" SELECT C FROM Client C WHERE C.name = :pName AND C.password = :pPassword")
					.setParameter("pName", toUser.getName())
					.setParameter("pPassword", PasswordEncryption.encrypt(password))
					.getSingleResult();
			
			return user != null && !user.getName().equals("");
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void save(TOClient to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void change(TOClient to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(TOClient to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TOClient findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TOClient> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TOClient> list(String specificType) {
		// TODO Auto-generated method stub
		return null;
	}
}
