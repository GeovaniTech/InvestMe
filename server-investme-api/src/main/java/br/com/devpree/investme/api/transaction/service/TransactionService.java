package br.com.devpree.investme.api.transaction.service;

import java.util.List;

import br.com.devpree.investme.api.common.AbstractService;
import br.com.devpree.investme.api.common.model.Transaction;
import br.com.devpree.investme.api.transaction.transferobject.TOTransactionRestModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class TransactionService extends AbstractService<Transaction, TOTransactionRestModel> {
	
	private static final long serialVersionUID = -8121558319003320132L;

	public TransactionService() {
		super(Transaction.class, TOTransactionRestModel.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TOTransactionRestModel> list(String email) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT T FROM ")
			.append(Transaction.class.getSimpleName())
			.append(" T ")
			.append(" WHERE T.client.email = :email ");
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Transaction.class);
		query.setParameter("email", email);
		
		return this.convertModelResults(query.getResultList());
	}

}
