package manter.investment;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import model.Transaction;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterInvestment extends AbstractManter implements IManterInvestmentSBean, IManterInvestmentSBeanRemote {
	private StringBuilder sql;
	
	public ManterInvestment() {
		this.sql = new StringBuilder();
		
		sql.append(" SELECT SUM(T.price * T.amount) ");
		sql.append(" FROM ").append(Transaction.class.getName()).append(" T ");
		sql.append(" WHERE T.typeTransaction = 'Investment' ");
		sql.append(" AND T.nameClient = :client");
	}
	
	@Override
	public Double spents() {		
		return em.createQuery(sql.toString(), Double.class)
				.setParameter("client", getClient().getEmail())
				.getSingleResult();
	}
	
	@Override
	public Double actions() {
		StringBuilder filters = new StringBuilder();
		filters.append(" AND T.typeActive.name = 'Action' ");
		
		return em.createQuery(sql.toString() + filters.toString(), Double.class)
				.setParameter("client", getClient().getEmail())
				.getSingleResult();
	}
	
	@Override
	public Double fiis() {
		StringBuilder filters = new StringBuilder();
		filters.append(" AND T.typeActive.name = 'Fii' ");
		
		return em.createQuery(sql.toString() + filters.toString(), Double.class)
				.setParameter("client", getClient().getEmail())
				.getSingleResult();
	}
	
	@Override
	public Double fixedIncome() {
		StringBuilder filters = new StringBuilder();
		filters.append(" AND T.typeActive.name = 'Fixed Income' ");
		
		return em.createQuery(sql.toString() + filters.toString(), Double.class)
				.setParameter("client", getClient().getEmail())
				.getSingleResult();
	}
	
	@Override
	public Double criptocurrencys() {
		StringBuilder filters = new StringBuilder();
		filters.append(" AND T.typeActive.name = 'Criptocurrency' ");
		
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
