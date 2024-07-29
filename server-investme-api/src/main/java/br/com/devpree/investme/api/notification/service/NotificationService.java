package br.com.devpree.investme.api.notification.service;

import java.util.ArrayList;
import java.util.List;

import br.com.devpree.investme.api.common.AbstractService;
import br.com.devpree.investme.api.common.model.Transaction;
import br.com.devpree.investme.api.notification.transferobject.TONotificationRestModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
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
		 
		sql.append(" SELECT TRIM(T.active) as active, ")
			.append(" SUM(T.price * T.amount) / SUM(T.amount) as avaragePrice ")
			.append(" FROM ").append(Transaction.class.getSimpleName()).append(" T ")
			.append(" WHERE T.category.type = 'investment' ")
			.append(" AND T.client.email = :email ")
			.append(" GROUP BY T.active ");
		
		Query query =  this.getEntityManager().createQuery(sql.toString(), Tuple.class);
		query.setParameter("email", email);
		
		List<TONotificationRestModel> notifications = new ArrayList<>();
		
		for(Tuple tuple : (List<Tuple>) query.getResultList()) {
			notifications.add(new TONotificationRestModel((String) tuple.get(0), null, (Double) tuple.get(1)));
		}
		
		return notifications;
	}
	
}
