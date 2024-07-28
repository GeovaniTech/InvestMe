package br.com.devpree.investme.api.notification.service;

import java.util.List;

import br.com.devpree.investme.api.common.AbstractService;
import br.com.devpree.investme.api.common.model.Transaction;
import br.com.devpree.investme.api.notification.transferobject.TONotificationRestModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class NotificationService extends AbstractService<Transaction, TONotificationRestModel> {

	private static final long serialVersionUID = -8110416910662022081L;

	public NotificationService() {
		super(Transaction.class, TONotificationRestModel.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TONotificationRestModel> getTransactionInvestments(String email) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT T.active, ")
			.append(" SUM(T.price * T.amount) / SUM(T.amount) as avaragePrice ")
			.append(" FROM ").append(Transaction.class.getSimpleName()).append(" T ")
			.append(" WHERE T.category.type = 'investment' ")
			.append(" AND T.client.email = :email ")
			.append(" GROUP BY T.active ");
		
		Query query =  this.getEntityManager().createQuery(sql.toString());
		query.setParameter("email", email);
		
		return this.convertModelResults(query.getResultList());
	}
	
}
