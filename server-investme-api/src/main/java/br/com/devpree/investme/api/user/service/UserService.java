package br.com.devpree.investme.api.user.service;

import br.com.devpree.investme.api.common.AbstractService;
import br.com.devpree.investme.api.common.model.Client;
import br.com.devpree.investme.api.user.model.TOUserRestModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class UserService extends AbstractService<Client, TOUserRestModel>{

	private static final long serialVersionUID = -4319531318041937076L;
	
	public UserService() { 
		super(Client.class, TOUserRestModel.class);
	}
	
	public TOUserRestModel auth(String email, String password) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT C FROM ")
			.append(Client.class.getSimpleName())
			.append(" C ")
			.append(" WHERE C.email = :email ")
			.append(" AND C.password = :password ");
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("email", email);
		query.setParameter("password", password);
		
		return query.getResultList().size() > 0 ? this.convertToDTO((Client)  query.getSingleResult()) : null;
	}
}
