package manter.investment;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import interfaces.JPAEntity;
import model.Transaction;
import utils.InvestmeSession;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterInvestment extends InvestmeSession implements JPAEntity, IManterInvestmentSBean, IManterInvestmentSBeanRemote {
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
				.setParameter("client", getClient().getName())
				.getSingleResult();
	}
	
	@Override
	public Double actions() {
		StringBuilder filters = new StringBuilder();
		filters.append(" AND T.typeActive.name = 'Action' ");
		
		return em.createQuery(sql.toString() + filters.toString(), Double.class)
				.setParameter("client", getClient().getName())
				.getSingleResult();
	}
	
	@Override
	public Double fiis() {
		StringBuilder filters = new StringBuilder();
		filters.append(" AND T.typeActive.name = 'Fii' ");
		
		return em.createQuery(sql.toString() + filters.toString(), Double.class)
				.setParameter("client", getClient().getName())
				.getSingleResult();
	}
	
	@Override
	public Double fixedIncome() {
		StringBuilder filters = new StringBuilder();
		filters.append(" AND T.typeActive.name = 'Fixed Income' ");
		
		return em.createQuery(sql.toString() + filters.toString(), Double.class)
				.setParameter("client", getClient().getName())
				.getSingleResult();
	}
	
	@Override
	public Double criptocurrencys() {
		StringBuilder filters = new StringBuilder();
		filters.append(" AND T.typeActive.name = 'Criptocurrency' ");
		
		return em.createQuery(sql.toString() + filters.toString(), Double.class)
				.setParameter("client", getClient().getName())
				.getSingleResult();
	}
	
	public StringBuilder getSql() {
		return sql;
	}

	public void setSql(StringBuilder sql) {
		this.sql = sql;
	}
}