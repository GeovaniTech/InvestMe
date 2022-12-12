package dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import dto.DTOActive;
import interfaces.GenericDAO;
import interfaces.JPAEntity;
import model.Active;
import model.Type;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DAOActive implements JPAEntity, GenericDAO<DTOActive>{

	@Override
	public void save(DTOActive model) {
		Active active = new Active();
		
		active.setName(model.getName());
		active.setPrice(model.getPrice());
		active.setType(model.getType());
		
		em.getTransaction().begin();
		em.persist(active);
		em.getTransaction().commit();
		
	}

	@Override
	public void change(DTOActive model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(DTOActive model) {
		em.getTransaction().begin();
		
		em.createQuery("DELETE FROM " + Active.class.getName() + " Active WHERE Active.id = :id")
			.setParameter("id", model.getId())
			.executeUpdate();
		
		em.getTransaction().commit();
	}

	@Override
	public DTOActive findById(int id) {
		Active active = em.find(Active.class, id);
		DTOActive dtoActive = new DTOActive();
		
		dtoActive.setId(active.getId());
		dtoActive.setName(active.getName());
		dtoActive.setPrice(active.getPrice());
		dtoActive.setType(active.getType());
		
		return dtoActive;
	}

	@Override
	public List<DTOActive> list() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT Active.id, ");
		sql.append(" Active.name, ");
		sql.append(" Active.price, ");
		sql.append(" Active.type ");
		sql.append(" FROM " + Active.class.getName());
		sql.append(" Active ");
		
		List<Object[]> result = em.createQuery(sql.toString(), Object[].class)
				.getResultList();
		
		List<DTOActive> convertedResults = new ArrayList<DTOActive>();
		
		for(Object[] o : result) {
			DTOActive dtoActive = new DTOActive();
			
			dtoActive.setId((Integer) o[0]);
			dtoActive.setName((String) o[1]);
			dtoActive.setPrice((double) o[2]);
			dtoActive.setType((Type) o[3]);
			
			convertedResults.add(dtoActive);
		}
		
		return convertedResults;
	}
	
}
