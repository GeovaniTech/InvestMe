package dao;

import java.util.List;

import dto.DTOTransaction;
import interfaces.GenericDAO;
import interfaces.JPAEntity;
import model.Transaction;

public class DAOTransaction implements JPAEntity, GenericDAO<DTOTransaction> {

	@Override
	public void save(DTOTransaction to) {
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
	public void change(DTOTransaction to) {
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
	public void remove(DTOTransaction to) {
		Transaction trasaction = em.find(Transaction.class, to.getId());
		
		em.getTransaction().begin();
		em.remove(em.contains(trasaction) ? trasaction : em.merge(trasaction));
		em.getTransaction().commit();
	}

	@Override
	public DTOTransaction findById(int id) {
		Transaction transaction = em.find(Transaction.class, id);
		DTOTransaction dtoTransaction = new DTOTransaction();
		
		dtoTransaction.setActive(transaction.getActive());
		dtoTransaction.setPrice(transaction.getPrice());
		dtoTransaction.setAmount(transaction.getAmount());
		dtoTransaction.setTypeActive(transaction.getTypeActive());
		dtoTransaction.setTypeTrasanction(transaction.getTypeTrasanction());
		dtoTransaction.setDate(transaction.getDate());
		
		return dtoTransaction;
	}

	@Override
	public List<DTOTransaction> list() {
		// TODO Auto-generated method stub
		return null;
	}

	public void validateFilters() {
		
	}
}
