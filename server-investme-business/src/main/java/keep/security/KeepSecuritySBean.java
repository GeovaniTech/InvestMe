package keep.security;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import model.Client;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepSecuritySBean implements IKeepSecuritySBean, IKeepSecurityRemoteSBean {
	@PersistenceContext(unitName = "investme_datasource")
	private EntityManager entityManager;

	@Override
	public boolean isClientBlocked(String email) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C FROM ")
			.append(Client.class.getSimpleName()).append(" C ")
			.append(" WHERE C.email = :pEmail ");
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("pEmail", email);
		
		Client client = (Client) query.getSingleResult();
		
		return client.isBlocked();
	}
	
	// Getters and Setters 
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}
