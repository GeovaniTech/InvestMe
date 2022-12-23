package manter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import interfaces.GenericDAO;
import interfaces.JPAEntity;
import model.Investment;
import model.Type;
import to.TOInvestment;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterInvestment implements JPAEntity, GenericDAO<TOInvestment> {

	@Override
	public void save(TOInvestment to) {
		Investment investment = new Investment();
		
		investment.setAmount(to.getAmount());
		investment.setDate(to.getDate());
		investment.setActive(to.getActive());
		investment.setActualPrice(to.getActualPrice());
		investment.setType(to.getType());
		
		em.getTransaction().begin();
		em.persist(investment);
		em.getTransaction().commit();
	}

	@Override
	public void change(TOInvestment to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(TOInvestment to) {
		em.getTransaction().begin();
		
		em.createQuery("DELETE FROM " + Investment.class.getName() +  " Investment WHERE Investment.id = :id")
			.setParameter("id", to.getId())
			.executeUpdate();
		
		em.getTransaction().commit();
	}

	@Override
	public TOInvestment findById(int id) {
		Investment investment = em.find(Investment.class, id);
		TOInvestment dtoInvestment = new TOInvestment();
		
		dtoInvestment.setId(investment.getId());
		dtoInvestment.setAmount(investment.getAmount());
		dtoInvestment.setDate(investment.getDate());
		
		return dtoInvestment;
	}

	@Override
	public List<TOInvestment> list() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT Investment.id, ");
		sql.append(" Investment.amount, ");
		sql.append(" Investment.active, ");
		sql.append(" Investment.actualPrice, ");
		sql.append(" Investment.date, ");
		sql.append(" Investment.type ");
		sql.append(" FROM " + Investment.class.getName() + " Investment");
		
		List<Object[]> result = em.createQuery(sql.toString(), Object[].class)
								.getResultList();
		
		List<TOInvestment> convertedResults =  new ArrayList<TOInvestment>();
		
		for(Object[] o : result) {
			TOInvestment dtoInvestment = new TOInvestment();
			
			dtoInvestment.setId((Integer) o[0]);
			dtoInvestment.setAmount((float) o[1]);
			dtoInvestment.setActive((String) o[2]);
			dtoInvestment.setActualPrice((Double) o[3]);
			dtoInvestment.setDate((Date) o[4]);
			dtoInvestment.setType((Type) o[5]);
			
			convertedResults.add(dtoInvestment);
		}
		
		return convertedResults;
	}
	
	public Double totalSpent() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT SUM(I.amount * I.actualPrice) ");
		sql.append(" FROM Investment I ");
		

		return 	em.createQuery(sql.toString(), Double.class)
				.getSingleResult();
	}
	
	public Double totalAction() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT SUM(I.amount * I.actualPrice) ");
		sql.append(" FROM Investment I ");
		sql.append(" WHERE I.type.name = 'Action' ");
		
		return 	em.createQuery(sql.toString(), Double.class)
				.getSingleResult();
	}

	public Double totalFixedIncome() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT SUM(I.amount * I.actualPrice) ");
		sql.append(" FROM Investment I ");
		sql.append(" WHERE I.type.name = 'Fixed Income' ");
		
		return 	em.createQuery(sql.toString(), Double.class)
				.getSingleResult();
	}
	
	public Double totalFii() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT SUM(I.amount * I.actualPrice) ");
		sql.append(" FROM Investment I ");
		sql.append(" WHERE I.type.name = 'Fii' ");
		
		return 	em.createQuery(sql.toString(), Double.class)
				.getSingleResult();
	}
	
	public Double totalCriptocurrency() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT SUM(I.amount * I.actualPrice) ");
		sql.append(" FROM Investment I ");
		sql.append(" WHERE I.type.name = 'Criptocurrency' ");
		

		return 	em.createQuery(sql.toString(), Double.class)
				.getSingleResult();
	}
}

