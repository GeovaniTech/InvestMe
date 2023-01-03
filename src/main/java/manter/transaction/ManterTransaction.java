package manter.transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import interfaces.JPAEntity;
import model.Transaction;
import model.Type;
import to.TOTransaction;
import utils.InvestmeSession;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterTransaction extends InvestmeSession implements JPAEntity, IManterTransactionSBean, IManterTransactionSBeanRemote  {
	
	@Override
	public void save(TOTransaction to) {
		Transaction transaction = new Transaction();
		
		transaction.setActive(to.getActive());
		transaction.setPrice(to.getPrice());
		transaction.setAmount(to.getAmount());
		transaction.setTypeActive(to.getTypeActive());
		transaction.setTypeTransaction(to.getTypeTransaction());
		transaction.setDate(to.getDate());
		transaction.setNameClient(getClient().getName());
		
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
		transaction.setNameClient(to.getNameClient());
		
		em.getTransaction().begin();
		em.merge(transaction);
		em.getTransaction().commit();
	}

	@Override
	public void remove(TOTransaction to) {
		em.getTransaction().begin();
		em.createQuery("DELETE FROM " + Transaction.class.getName() + " T WHERE T.id = :id")
		.setParameter("id", to.getId())
		.executeUpdate();
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
		dtoTransaction.setNameClient(transaction.getNameClient());
		
		return dtoTransaction;
	}
	
	@Override
	public List<TOTransaction> list() {
		return null;
	}
	
	@Override
	public List<TOTransaction> list(String typeTransacion) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT T.id, ");
		sql.append(" T.active, ");
		sql.append(" T.price, ");
		sql.append(" T.amount, ");
		sql.append(" T.date, ");
		sql.append(" T.typeActive, ");
		sql.append(" T.typeTransaction, ");
		sql.append(" T.nameClient");
		sql.append(" FROM ").append(Transaction.class.getName()).append(" T ");
		sql.append(" WHERE T.nameClient = :client");
		
		List<Object[]> result = new ArrayList<Object[]>();
		List<TOTransaction> convertedTransactions = new ArrayList<TOTransaction>();
		
		if(!typeTransacion.equals("")) {
			
			sql.append(" AND T.typeTransaction = :pTypeTransaction ");
			
			
			result = em.createQuery(sql.toString(), Object[].class)
					.setParameter("pTypeTransaction", typeTransacion)
					.setParameter("client", getClient().getName())
					.getResultList();
		} else {		
			result = em.createQuery(sql.toString(), Object[].class)
					.setParameter("client", getClient().getName())
					.getResultList();
		}
		
		for(Object[] o : result) {
			TOTransaction to = new TOTransaction();
			
			to.setId((int) o[0]);
			to.setActive((String) o[1]);
			to.setPrice((Double) o[2]);
			to.setAmount((Integer) o[3]);
			to.setDate((Date) o[4]);
			to.setTypeActive((Type) o[5]);
			to.setTypeTransaction((String) o[6]);
			to.setNameClient((String) o[7]);
			
			convertedTransactions.add(to);
		}
		
		return convertedTransactions;
	}
}
