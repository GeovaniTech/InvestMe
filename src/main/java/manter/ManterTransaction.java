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
	
	@Override
	public void save(TOTransaction to) {
		Transaction transaction = new Transaction();
		
		transaction.setActive(to.getActive());
		transaction.setPrice(to.getPrice());
		transaction.setAmount(to.getAmount());
		transaction.setTypeActive(to.getTypeActive());
		transaction.setTypeTransaction(to.getTypeTransaction());
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
		transaction.setTypeTransaction(to.getTypeTransaction());
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
		dtoTransaction.setTypeTransaction(transaction.getTypeTransaction());
		dtoTransaction.setDate(transaction.getDate());
		
		return dtoTransaction;
	}
	
	@Override
	public List<TOTransaction> list() {
		return null;
	}
	
	@Override
	public List<TOTransaction> list(String typeTransacion) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT T.active, ");
		sql.append(" T.price, ");
		sql.append(" T.amount, ");
		sql.append(" T.date, ");
		sql.append(" T.typeActive, ");
		sql.append(" T.typeTransaction ");
		sql.append(" FROM ").append(Transaction.class.getName()).append(" T ");
		
		List<Object[]> result = new ArrayList<Object[]>();
		List<TOTransaction> convertedTransactions = new ArrayList<TOTransaction>();
		
		if(!typeTransacion.equals("")) {
			
			sql.append(" WHERE T.typeTransaction = :pTypeTransaction ");
			
			result = em.createQuery(sql.toString(), Object[].class)
					.setParameter("pTypeTransaction", typeTransacion)
					.getResultList();
		} else {
			result = em.createQuery(sql.toString(), Object[].class)
					.getResultList();
		}
		
		for(Object[] o : result) {
			TOTransaction to = new TOTransaction();
			
			to.setActive((String) o[0]);
			to.setPrice((Double) o[1]);
			to.setAmount((Integer) o[2]);
			to.setDate((Date) o[3]);
			to.setTypeActive((Type) o[4]);
			to.setTypeTransaction((String) o[5]);
			
			convertedTransactions.add(to);
		}
		
		return convertedTransactions;
	}
}
