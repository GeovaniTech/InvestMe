package dao;

import java.util.List;

import interfaces.JPA;
import model.Type;

public class DAOType implements JPA {
	public void save(Type type) {
		em.getTransaction().begin();
		em.persist(type);
		em.getTransaction().commit();
	}
	
	public void remove(Type type) {
		em.getTransaction().begin();
		em.remove(em.contains(type) ? type : em.merge(type));
		em.getTransaction().commit();
	}
	
	public Type findById(int id) {
		return em.find(Type.class, id);
	}
	
	public List<Type> listTypes() {
		String sql = "SELECT T FROM Type T";
		
		return em.createQuery(sql, Type.class)
				.getResultList();
	}
}
