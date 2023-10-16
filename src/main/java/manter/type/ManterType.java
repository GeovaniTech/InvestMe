package manter.type;

import java.util.ArrayList;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import model.Type;
import to.TOType;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterType extends AbstractManter implements IManterTypeSBean, IManterTypeSBeanRemote  {
	@Override
	public void save(TOType model) {
		Type type = new Type(); 
		
		type.setName(model.getName());
		type.setNameClient(getClient().getEmail());
		type.setTypeTransaction(model.getTypeTransaction());
		
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
		dtoType.setNameClient(type.getNameClient());
		return dtoType;
	}
	
	@Override
	public List<TOType> list() {
		List<TOType> convertedResults = new ArrayList<TOType>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT T.id, ");
		sql.append(" T.name, ");
		sql.append(" T.nameClient, ");
		sql.append(" T.typeTransaction ");
		sql.append(" FROM ").append(Type.class.getName()).append(" T ");
		sql.append(" WHERE T.nameClient = :client OR T.nameClient IS NULL");
		
		List<Object[]> result = em.createQuery(sql.toString(), Object[].class)
								.setParameter("client", getClient().getEmail())
								.getResultList();
		
		for(Object[] o : result) {
			TOType to = new TOType();
			
			to.setId((Integer) o[0]);
			to.setName((String) o[1]);
			to.setNameClient((String) o[2]);
			to.setTypeTransaction((String) o[3]);
			System.out.print(to.getName());
			to.setSpents(totalSpents(to.getName()));
			convertedResults.add(to);
		}
		
		return convertedResults;
	}

	@Override
	public List<TOType> list(String specificType) {
		List<TOType> convertedResults = new ArrayList<TOType>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT T.id, ");
		sql.append(" T.name, ");
		sql.append(" T.nameClient, ");
		sql.append(" T.typeTransaction ");
		sql.append(" FROM ").append(Type.class.getName()).append(" T ");
		sql.append(" WHERE T.typeTransaction = :typeTransaction AND T.nameClient = :client ");
		
		List<Object[]> result = em.createQuery(sql.toString(), Object[].class)
								.setParameter("client", getClient().getEmail())
								.setParameter("typeTransaction", specificType)
								.getResultList();
		
		for(Object[] o : result) {
			TOType to = new TOType();
			
			to.setId((Integer) o[0]);
			to.setName((String) o[1]);
			to.setNameClient((String) o[2]);
			to.setTypeTransaction((String) o[3]);
			to.setSpents(totalSpents(to.getName()));
			convertedResults.add(to);
		}
		
		return convertedResults;
	}

	@Override
	public Double totalSpents(String type) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT SUM(T.price * T.amount) ");
		sql.append(" FROM ").append(model.Transaction.class.getName()).append(" T ");
		sql.append(" WHERE T.typeActive.name = :name ");
		
		try {
			return em.createQuery(sql.toString(), Double.class)
					.setParameter("name", type)
					.getSingleResult();
		} catch (Exception e) {
			return 0.0;
		}
	}	
}
