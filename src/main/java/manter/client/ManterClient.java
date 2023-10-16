package manter.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Query;

import interfaces.GenericDAO;
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
		
		MessageUtil.sendMessage("", null);
		
		return false;
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
