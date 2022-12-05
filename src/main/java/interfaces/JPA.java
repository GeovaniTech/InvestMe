package interfaces;

import javax.persistence.EntityManager;

import utils.JPAUtil;

public interface JPA {
	EntityManager em = JPAUtil.getEntityManager();
}
