package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import dto.DTOInvestment;
import interfaces.GenericDAO;
import interfaces.JPAEntity;
import model.Active;
import model.Investment;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DAOInvestment implements JPAEntity, GenericDAO<DTOInvestment> {

	@Override
	public void save(DTOInvestment to) {
		Investment investment = new Investment();
		
		investment.setActive(to.getActive());
		investment.setAmount(to.getAmount());
		investment.setDate(to.getDate());
		
		em.getTransaction().begin();
		em.persist(investment);
		em.getTransaction().commit();
	}

	@Override
	public void change(DTOInvestment to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(DTOInvestment to) {
		em.getTransaction().begin();
		
		em.createQuery("DELETE FROM " + Investment.class.getName() +  " Investment WHERE Investment.id = :id")
			.setParameter("id", to.getId())
			.executeUpdate();
		
		em.getTransaction().commit();
	}

	@Override
	public DTOInvestment findById(int id) {
		Investment investment = em.find(Investment.class, id);
		DTOInvestment dtoInvestment = new DTOInvestment();
		
		dtoInvestment.setId(investment.getId());
		dtoInvestment.setActive(investment.getActive());
		dtoInvestment.setAmount(investment.getAmount());
		dtoInvestment.setDate(investment.getDate());
		
		return dtoInvestment;
	}

	@Override
	public List<DTOInvestment> list() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT Investment.id, ");
		sql.append(" Investment.active, ");
		sql.append(" Investment.amount, ");
		sql.append(" Investment.date ");
		sql.append(" FROM " + Investment.class.getName() + " Investment");
		
		List<Object[]> result = em.createQuery(sql.toString(), Object[].class)
								.getResultList();
		
		List<DTOInvestment> convertedResults =  new ArrayList<DTOInvestment>();
		
		for(Object[] o : result) {
			DTOInvestment dtoInvestment = new DTOInvestment();
			
			dtoInvestment.setId((Integer) o[0]);
			dtoInvestment.setActive((Active) o[1]);
			dtoInvestment.setAmount((float) o[2]);
			dtoInvestment.setDate((Date) o[3]);
			
			convertedResults.add(dtoInvestment);
		}
		
		return convertedResults;
	}
	
}
