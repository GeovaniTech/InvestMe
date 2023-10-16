package utils;

import jakarta.ejb.Stateless;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
