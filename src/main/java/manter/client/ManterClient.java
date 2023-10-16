package manter.client;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.faces.application.FacesMessage;

import interfaces.GenericDAO;
import jakarta.persistence.Query;
import model.Client;
import to.TOClient;
import utils.AbstractManter;
import utils.Encryption;
import utils.MessageUtil;
import utils.RedirectUrl;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterClient extends AbstractManter implements GenericDAO<TOClient>, IManterClientSBean, IManterClientSbeanRemote {
	
	@Override
	public boolean logar(String email, String password) {
		try {
			StringBuilder sql = new StringBuilder();
			
			sql.append(" SELECT C FROM ")
			   .append(Client.class.getName()).append(" C ")
			   .append(" WHERE C.email = :email AND C.password = :password ");
			
			Query query = em.createQuery(sql.toString(), Client.class);
			query.setParameter("email", email);
			query.setParameter("password", Encryption.encryptTextSHA(password));
			
			Client client = (Client) query.getSingleResult();
			
			TOClient to = new TOClient();
			
			to.setEmail(client.getEmail());
			
			this.getSession().setAttribute("client", to);
			
			RedirectUrl.redirectTo("/investme/home/investments");
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("user_or_password_incorrect"), FacesMessage.SEVERITY_ERROR);
		
		return false;
	}
	
	@Override
	public void register(String email, String password) {
		Client client = new Client();
		
		client.setEmail(email);
		client.setPassword(Encryption.encryptTextSHA(password));
		
	    em.getTransaction().begin();
	    em.persist(client);
	    em.getTransaction().commit();
	}

	public boolean verifyIfExistsUserByEmail(String email) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C FROM ")
			.append(Client.class.getName()).append(" C ")
			.append(" WHERE C.email = :email ");
		
		Query query = this.em.createQuery(sql.toString(), Client.class);
		query.setParameter("email", email);
		
		return query.getResultList().size() > 0;
	}

	@Override
	public void save(TOClient to) {
		
		
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
