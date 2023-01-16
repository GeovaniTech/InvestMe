package utils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public abstract class AbstractManter extends InvestmeSession {
	@PersistenceContext(unitName = "investme")
	public EntityManager em;
	public Messages msg;
	
	public AbstractManter() {
		this.msg = new Messages();
		
		if(em == null) {
			em = Jpa.getEntityManager();
		}
	}

	public Messages getMsg() {
		return msg;
	}

	public void setMsg(Messages msg) {
		this.msg = msg;
	}
}
