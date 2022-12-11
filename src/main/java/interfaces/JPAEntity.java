package interfaces;

import javax.persistence.EntityManager;

import utils.JPAUtil;

public interface JPAEntity {
	public EntityManager em = JPAUtil.getEntityManager();
}
