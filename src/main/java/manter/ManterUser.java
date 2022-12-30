package manter;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import interfaces.GenericDAO;
import interfaces.JPAEntity;
import model.User;
import to.TOUser;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterUser implements GenericDAO<TOUser>, JPAEntity {
	
	public boolean validateAccess(TOUser toUser) {
		User user = (User) em.createQuery(" SELECT U FROM User U WHERE U.name = :pName AND U.password = :pPassword")
			.setParameter("pName", toUser.getName())
			.setParameter("pPassword", toUser.getPassword())
			.getSingleResult();
		
		return user != null && !user.getName().equals("");
			
	}

	@Override
	public void save(TOUser to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void change(TOUser to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(TOUser to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TOUser findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TOUser> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TOUser> list(String specificType) {
		// TODO Auto-generated method stub
		return null;
	}
}
