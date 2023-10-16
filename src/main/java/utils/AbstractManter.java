package utils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public abstract class AbstractManter extends InvestmeSession {
	@PersistenceContext(unitName = "investme")
	public EntityManager em;
	
	public AbstractManter() {
		if(em == null) {
			em = Jpa.getEntityManager();
		}
	}
	
}
