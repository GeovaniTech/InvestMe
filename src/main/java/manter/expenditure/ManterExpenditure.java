package manter.expenditure;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import model.Transaction;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterExpenditure extends AbstractManter implements IManterExpenditureSBean, IManterExpenditureSBeanRemote {
	private StringBuilder sql;
	
	public ManterExpenditure() {
		this.sql = new  StringBuilder();
		
		sql.append(" SELECT SUM(T.price * T.amount) ");
		sql.append(" FROM ").append(Transaction.class.getName()).append(" T ");
		sql.append(" WHERE T.typeTransaction = 'Expenditure' ");
		sql.append(" AND T.nameClient = :client ");
	}
	
	@Override
	public Double spents() {
		return em.createQuery(sql.toString(), Double.class)
				.setParameter("client", getClient().getEmail())
				.getSingleResult();
	}
	
	@Override
	public Double recreation() {
		StringBuilder filters = new StringBuilder();
		filters.append(" AND T.typeActive.name = 'Recreation' ");
		
		return em.createQuery(sql.toString() + filters.toString(), Double.class)
				.setParameter("client", getClient().getEmail())
				.getSingleResult();
	}
	
	@Override
	public Double market() {
		StringBuilder filters = new StringBuilder();
		filters.append(" AND T.typeActive.name = 'Market' ");
		
		return em.createQuery(sql.toString() + filters.toString(), Double.class)
				.setParameter("client", getClient().getEmail())
				.getSingleResult();
	}
	
	@Override
	public Double ifood() {
		StringBuilder filters = new StringBuilder();
		filters.append(" AND T.typeActive.name = 'Ifood' ");
		
		return em.createQuery(sql.toString() + filters.toString(), Double.class)
				.setParameter("client", getClient().getEmail())
				.getSingleResult();
	}
	
	@Override
	public Double others() {
		StringBuilder filters = new StringBuilder();
		filters.append(" AND T.typeActive.name = 'Others' ");
		
		return em.createQuery(sql.toString() + filters.toString(), Double.class)
				.setParameter("client", getClient().getEmail())
				.getSingleResult();
	}

	public StringBuilder getSql() {
		return sql;
	}

	public void setSql(StringBuilder sql) {
		this.sql = sql;
	}
}

