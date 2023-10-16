package manter.register;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import model.Client;
import utils.AbstractManter;
import utils.PasswordEncryption;
import utils.RedirectUrl;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterRegister extends AbstractManter implements IManterRegisterSBean, IManterRegisterSBeanRemote {

	@Override
	public boolean register(String user, String password, String repeatPassword) {
		if(!password.equals(repeatPassword)) {
			return false;
		}
		
		if(validateUserName(user)) {
			Client client = new Client();
			
			//client.setName(user);
			client.setPassword(PasswordEncryption.encrypt(password));
			
			em.getTransaction().begin();
			em.persist(client);
			em.getTransaction().commit();	
			
			RedirectUrl.redirectTo("/investme/login.xhtml");
			return true;
		} else {
			
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
