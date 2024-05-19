package keep.payment;

import java.util.ArrayList;
import java.util.List;

import abstracts.AbstractKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import model.Payment;
import query.SimpleWhere;
import to.TOParameter;
import to.payment.TOFilterPayment;
import to.payment.TOPayment;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepPaymentSBean extends AbstractKeep<Payment, TOPayment> implements IKeepPaymentSBean, IKeepPaymentRemoteSBean {
	
	public KeepPaymentSBean() {
		super(Payment.class, TOPayment.class);
	}

	@Override
	public void save(TOPayment payment) {
		Payment model = this.convertToModel(payment);
		
		this.getEntityManager().persist(model);
		payment.setId(model.getId());
	}

	@Override
	public void change(TOPayment payment) {
		Payment model = this.convertToModel(payment);
		
		this.getEntityManager().merge(model);
	}

	@Override
	public void remove(TOPayment payment) {
		Payment model = this.convertToModel(payment);
		
		this.getEntityManager().remove(this.getEntityManager().contains(model) ? model : this.getEntityManager().merge(model));
	}

	@Override
	public TOPayment findById(int id) {
		return this.convertToDTO(this.getEntityManager().find(Payment.class, id));
	}

	@Override
	public Integer getCount(TOFilterPayment filter) {
		StringBuilder sql = new StringBuilder();
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT COUNT(P.id) ");
		sql.append(this.getFromPayments());
		sql.append(this.getWherePayments(filter, params));
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		setParameters(query, params);
		
		Number value = (Number) query.getSingleResult();
		
		return value.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TOPayment> search(TOFilterPayment filter) {
		StringBuilder sql = new StringBuilder();
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT P ");
		sql.append(this.getFromPayments());
		sql.append(this.getWherePayments(filter, params));
		
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setFirstResult(filter.getFirstResult());
		query.setMaxResults(filter.getMaxResults());
		setParameters(query, params);
		
		return this.convertModelResults(query.getResultList());
	}
	
	private String getFromPayments() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" FROM ");
		sql.append(Payment.class.getSimpleName()).append(" P ");
		
		return sql.toString();
	}
	
	private String getWherePayments(TOFilterPayment filter, List<TOParameter> params) {
		StringBuilder sql = new StringBuilder();

		sql.append(" WHERE P.creationUser = :email");
		params.add(new TOParameter("email", this.getClientSession().getEmail()));
		
		sql.append(SimpleWhere.queryFilter("P.name", filter.getName()));
		
		return sql.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TOPayment> listAll() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P ");
		sql.append(this.getFromPayments());
		sql.append(" WHERE P.creationUser = :email");
		sql.append(" ORDER BY P.name ASC ");
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("email", this.getClientSession().getEmail());
		
		return this.convertModelResults(query.getResultList());
	}

	@Override
	public void deletePaymentsFromUser() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE ").append(this.getFromPayments());
		sql.append(" WHERE P.creationUser = :email");
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("email", this.getClientSession().getEmail());
		
		query.executeUpdate();
	}
	
}
