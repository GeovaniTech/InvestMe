package manter;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import interfaces.JPAEntity;
import model.Transaction;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterExpenditure implements JPAEntity {
	private StringBuilder sql;
	
	public ManterExpenditure() {
		this.sql = new  StringBuilder();
		
		sql.append(" SELECT SUM(T.price * T.amount) ");
		sql.append(" FROM ").append(Transaction.class.getName()).append(" T ");
		sql.append(" WHERE T.typeTransaction = 'Expenditure' ");
	}
	
	// Dashboard
	public Double spents() {
		return em.createQuery(sql.toString(), Double.class).getSingleResult();
	}
	
	public Double recreation() {
		StringBuilder filters = new StringBuilder();
		filters.append(" AND T.typeActive.name = 'Recreation' ");
		
		return em.createQuery(sql.toString() + filters.toString(), Double.class).getSingleResult();
	}
	
	public Double market() {
		StringBuilder filters = new StringBuilder();
		filters.append(" AND T.typeActive.name = 'Market' ");
		
		return em.createQuery(sql.toString() + filters.toString(), Double.class).getSingleResult();
	}

	public Double ifood() {
		StringBuilder filters = new StringBuilder();
		filters.append(" AND T.typeActive.name = 'Ifood' ");
		
		return em.createQuery(sql.toString() + filters.toString(), Double.class).getSingleResult();
	}
	
	public Double others() {
		StringBuilder filters = new StringBuilder();
		filters.append(" AND T.typeActive.name = 'Others' ");
		
		return em.createQuery(sql.toString() + filters.toString(), Double.class).getSingleResult();
	}

	public StringBuilder getSql() {
		return sql;
	}

	public void setSql(StringBuilder sql) {
		this.sql = sql;
	}
}

