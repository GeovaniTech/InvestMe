package dao;

import java.util.ArrayList;
import java.util.List;
import dto.DTOType;
import interfaces.GenericDAO;
import interfaces.JPAEntity;
import model.Type;

public class DAOType implements JPAEntity, GenericDAO<DTOType> {
	@Override
	public void save(DTOType model) {
		Type type = new Type(); 
		
		type.setName("teste");
		
		em.getTransaction().begin();
		em.persist(type);
		em.getTransaction().commit();
	}

	@Override
	public void change(DTOType model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(DTOType model) {
		em.getTransaction().begin();
		
		em.createQuery("DELETE FROM " + Type.class.getName() + " Type WHERE Type.id = :id")
			.setParameter("id", model.getId())
			.executeUpdate();
	
		em.getTransaction().commit();
	}

	@Override
	public DTOType findById(int id) {
		Type type = em.find(Type.class, id);
		DTOType dtoType = new DTOType();
		
		dtoType.setId(type.getId());
		dtoType.setName(type.getName());
		return dtoType;
	}
	@Override
	public List<DTOType> list() {
		List<DTOType> convertedResults = new ArrayList<DTOType>();
		
		List<Object[]> result = em.createQuery("SELECT Type.id, Type.name FROM " + Type.class.getName() + " Type", Object[].class)
								.getResultList();
		
		for(Object[] o : result) {
			DTOType to = new DTOType();
			
			to.setId((Integer) o[0]);
			to.setName((String) o[1]);
			
			System.out.println("NAME " + to.getName());
			
			convertedResults.add(to);
		}
		
		return convertedResults;
	}	
	
}
