package manter;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import interfaces.GenericDAO;
import interfaces.JPAEntity;
import model.Type;
import to.TOType;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterType implements JPAEntity, GenericDAO<TOType> {
	@Override
	public void save(TOType model) {
		Type type = new Type(); 
		
		type.setName(model.getName());
		
		em.getTransaction().begin();
		em.persist(type);
		em.getTransaction().commit();
	}

	@Override
	public void change(TOType model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(TOType model) {
		em.getTransaction().begin();
		
		em.createQuery("DELETE FROM " + Type.class.getName() + " Type WHERE Type.id = :id")
			.setParameter("id", model.getId())
			.executeUpdate();
	
		em.getTransaction().commit();
	}

	@Override
	public TOType findById(int id) {
		Type type = em.find(Type.class, id);
		TOType dtoType = new TOType();
		
		dtoType.setId(type.getId());
		dtoType.setName(type.getName());
		return dtoType;
	}
	
	@Override
	public List<TOType> list() {
		List<TOType> convertedResults = new ArrayList<TOType>();
		
		List<Object[]> result = em.createQuery("SELECT Type.id, Type.name FROM " + Type.class.getName() + " Type", Object[].class)
								.getResultList();
		
		for(Object[] o : result) {
			TOType to = new TOType();
			
			to.setId((Integer) o[0]);
			to.setName((String) o[1]);
			
			convertedResults.add(to);
		}
		
		return convertedResults;
	}

	@Override
	public List<TOType> list(String specificType) {
		// TODO Auto-generated method stub
		return null;
	}	
	
}
