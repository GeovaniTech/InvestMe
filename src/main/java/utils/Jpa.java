package utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnit;

public class Jpa {
	@PersistenceUnit(unitName = "investme")
	private static EntityManagerFactory emf = null;

	static {
		
		if(emf == null) {
			emf = Persistence.createEntityManagerFactory("investme");
		}
	}
	
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}