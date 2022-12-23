package manter;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import interfaces.GenericDAO;
import interfaces.JPAEntity;
import model.Transaction;
import model.Type;
import to.TOTransaction;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterTransaction implements JPAEntity, GenericDAO<TOTransaction> {

	TOTransaction filters;
	List<String> parameters; 
	
	public ManterTransaction() {
		this.filters = new TOTransaction();
		this.parameters = new ArrayList<String>();
	}
	
	@Override
	public void save(TOTransaction to) {
		Transaction transaction = new Transaction();
		
		transaction.setActive(to.getActive());
		transaction.setPrice(to.getPrice());
		transaction.setAmount(to.getAmount());
		transaction.setTypeActive(to.getTypeActive());
		transaction.setTypeTrasanction(to.getTypeTrasanction());
		transaction.setDate(to.getDate());
		
		em.getTransaction().begin();
		em.persist(transaction);
		em.getTransaction().commit();
	}

	@Override
	public void change(TOTransaction to) {
		Transaction transaction = new Transaction();
		
		transaction.setId(to.getId());
		transaction.setActive(to.getActive());
		transaction.setPrice(to.getPrice());
		transaction.setAmount(to.getAmount());
		transaction.setTypeActive(to.getTypeActive());
		transaction.setTypeTrasanction(to.getTypeTrasanction());
		transaction.setDate(to.getDate());
		
		em.getTransaction().begin();
		em.merge(transaction);
		em.getTransaction().commit();
	}

	@Override
	public void remove(TOTransaction to) {
		Transaction trasaction = em.find(Transaction.class, to.getId());
		
		em.getTransaction().begin();
		em.remove(em.contains(trasaction) ? trasaction : em.merge(trasaction));
		em.getTransaction().commit();
	}

	@Override
	public TOTransaction findById(int id) {
		Transaction transaction = em.find(Transaction.class, id);
		TOTransaction dtoTransaction = new TOTransaction();
		
		dtoTransaction.setActive(transaction.getActive());
		dtoTransaction.setPrice(transaction.getPrice());
		dtoTransaction.setAmount(transaction.getAmount());
		dtoTransaction.setTypeActive(transaction.getTypeActive());
		dtoTransaction.setTypeTrasanction(transaction.getTypeTrasanction());
		dtoTransaction.setDate(transaction.getDate());
		
		return dtoTransaction;
	}

	@Override
	public List<TOTransaction> list() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT T.active, ");
		sql.append(" T.price, ");
		sql.append(" T.amount, ");
		sql.append(" T.typeActive, ");
		sql.append(" T.typeTransaction, ");
		sql.append(" T.date ");
		sql.append(" FROM " + Transaction.class.getName() + " T ");
		
		if(this.validateFilters() != null) {
			sql.append(this.validateFilters());
		}
		
		List<Object[]> result = new ArrayList<Object[]>();
		
		result = em.createQuery(sql.toString(), Object[].class)
					.getResultList();
		
		List<TOTransaction> convertedResults = new ArrayList<>();
		
		for(Object[] o : result) {
			TOTransaction dtoTrasanction = new TOTransaction();
			
			dtoTrasanction.setActive((String) o[0]);
			dtoTrasanction.setPrice((Double) o[1]);
			dtoTrasanction.setAmount((Integer) o[2]);
			dtoTrasanction.setTypeActive((Type) o[3]);
			dtoTrasanction.setTypeTrasanction((String) o[4]);
			dtoTrasanction.setDate((Date) o[5]);
			
			convertedResults.add(dtoTrasanction);
		}		
		return convertedResults;
	}

	public String validateFilters() {
		StringBuilder sql = new StringBuilder();
		
		if(filters.getTypeTrasanction() == null) {
			sql.append(" WHERE T.typeTrasanction IS NOT NULL ");
		} else {
			sql.append(" ");
		}
		
		if(filters.getActive() != null) {
			
		}
		
		return null;
	}

	public TOTransaction getFilters() {
		return filters;
	}

	public void setFilters(TOTransaction filters) {
		this.filters = filters;
	}
}
