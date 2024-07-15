package br.com.devpree.investme.api.payments.service;

import java.util.List;

import br.com.devpree.investme.api.common.AbstractService;
import br.com.devpree.investme.api.common.model.Payment;
import br.com.devpree.investme.api.payments.transferobject.TOPaymentRestModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class PaymentService extends AbstractService<Payment, TOPaymentRestModel> {
	
	private static final long serialVersionUID = 258277886950868095L;

	public PaymentService() {
		super(Payment.class, TOPaymentRestModel.class);
	}

	@SuppressWarnings("unchecked")
	public List<TOPaymentRestModel> list(String email) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT P FROM ")
			.append(Payment.class.getSimpleName())
			.append(" P ")
			.append(" WHERE P.creationUser = :email ");
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Payment.class);
		query.setParameter("email", email);
		
		return this.convertModelResults(query.getResultList());
	}
}
