package manter.register;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import interfaces.JPAEntity;
import interfaces.Messages;
import model.Client;
import utils.RedirectUrl;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterRegister implements JPAEntity, Messages, IManterRegisterSBean, IManterRegisterSBeanRemote {

	@Override
	public boolean register(String user, String password, String repeatPassword) {
		if(!password.equals(repeatPassword)) {
			msg.passwordsDontMatch();
			
			return false;
		}
		
		if(validateUserName(user)) {
			Client client = new Client();
			
			client.setName(user);
			client.setPassword(password);
			
			em.getTransaction().begin();
			em.persist(client);
			em.getTransaction().commit();	
			
			RedirectUrl.redirecionarPara("/investme/login.xhtml");
			return true;
		} else {
			msg.erroNameUsed();
		}
		
		return false;
	}
	
	public boolean validateUserName(String userName) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT C ");
		sql.append(" FROM ").append(Client.class.getName()).append(" C ");
		sql.append(" WHERE C.name = :name");
		
		try {
			return em.createQuery(sql.toString(), Client.class)
			.setParameter("name", userName)
			.getSingleResult() == null;
		} catch (Exception e) {
			return true;
		}
	}
}