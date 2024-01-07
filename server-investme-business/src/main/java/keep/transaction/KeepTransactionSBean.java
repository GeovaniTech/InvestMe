package keep.transaction;

import java.util.ArrayList;
import java.util.List;

import abstracts.AbstractKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;
import model.Transaction;
import query.SimpleWhere;
import to.TOParameter;
import to.category.TOCategory;
import to.transaction.TOFilterTransaction;
import to.transaction.TOTransaction;
import utils.StringUtil;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepTransactionSBean extends AbstractKeep<Transaction, TOTransaction> implements IKeepTransactionSBean, IKeepTransactionRemoteSBean {
	public KeepTransactionSBean() {
		super(Transaction.class, TOTransaction.class);
	}

	@Override
	public void save(TOTransaction transaction) {
		Transaction model = this.convertToModel(transaction);
		
		this.getEntityManager().persist(model);
		transaction.setId(model.getId());
	}

	@Override
	public void change(TOTransaction transaction) {
		Transaction model = this.convertToModel(transaction);
		
		this.getEntityManager().merge(model);
	}

	@Override
	public void remove(TOTransaction transaction) {
		Transaction model = this.convertToModel(transaction);
		
		this.getEntityManager().remove(this.getEntityManager().contains(model) ? model : this.getEntityManager().merge(model));
	}

	@Override
	public TOTransaction findById(int id) {
		return this.convertToDTO(this.getEntityManager().find(Transaction.class, id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TOTransaction> search(TOFilterTransaction filter) {
		StringBuilder sql = new StringBuilder();
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT T ")
			.append(this.getFromTransactions())
			.append(this.getWhereTransactions(filter, params))
			.append(this.getOrderByTransactions());
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setFirstResult(filter.getFirstResult());
		query.setMaxResults(filter.getMaxResults());
		
		setParameters(query, params);
		
		return this.convertModelResults(query.getResultList());
	}

	@Override
	public Integer getCount(TOFilterTransaction filter) {
		StringBuilder sql = new StringBuilder();
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT COUNT(T.id) ")
			.append(this.getFromTransactions())
			.append(this.getWhereTransactions(filter, params));
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		setParameters(query, params);
		
		Number value = (Number) query.getSingleResult();
		
		return value.intValue();
	}
	
	private String getFromTransactions() {
		StringBuilder sql = new StringBuilder();
		sql.append(" FROM ")
			.append(Transaction.class.getSimpleName())
			.append(" T ");
		
		return sql.toString();
	}
	
	private String getWhereTransactions(TOFilterTransaction filter, List<TOParameter> params) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" WHERE T.client.id = :idClient ");
		
		params.add(new TOParameter("idClient", this.getClientSession().getId()));
		
		sql.append(SimpleWhere.queryFilter("T.active", filter.getActive()));
		sql.append(SimpleWhere.queryFilterDateRange("T.datePurchase", filter.getDatePurchase(), TemporalType.DATE, params));
		sql.append(SimpleWhere.queryFilterNumberRange("T.price", filter.getPrice()));
		
		if(StringUtil.isNotNull(filter.getType())) {
			sql.append(" AND T.category.type = :type ");
			params.add(new TOParameter("type", filter.getType()));
		}
		
		if(filter.getIdCategory() != null) {
			sql.append(" AND T.category.id = :idCategory ");
			params.add(new TOParameter("idCategory", filter.getIdCategory()));
		}
		
		if(filter.getIdPayment() != null) {
			sql.append(" AND T.payment.id = : idPayment ");
			params.add(new TOParameter("idPayment", filter.getIdPayment()));
		}
		
		return sql.toString();
	}
	
	private String getOrderByTransactions() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" ORDER BY T.datePurchase DESC ");
		
		return sql.toString();
	}
	
	@Override
	public Double getTotalSpentByCategory(TOFilterTransaction filter, TOCategory category) {
		StringBuilder sql = new StringBuilder();
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT SUM(T.price * T.amount) ")
			.append(this.getFromTransactions())
			.append(this.getWhereTransactions(filter, params))
			.append(" AND T.category.id = :idCategory ");
		
		params.add(new TOParameter("idCategory", category.getId()));
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		setParameters(query, params);
		
		Number value = (Number) query.getSingleResult();
		
		return value != null ? value.doubleValue() : 0.0;
	}

	@Override
	public Double getTotalByType(TOFilterTransaction filter, String type) {
		StringBuilder sql = new StringBuilder();
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT SUM(T.price * T.amount) ")
			.append(this.getFromTransactions())
			.append(this.getWhereTransactions(filter, params))
			.append(" AND T.category.type = :type");
		
		params.add(new TOParameter("type", type));
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		setParameters(query, params);
		
		Number value = (Number) query.getSingleResult();
		
		return value != null ? value.doubleValue() : 0.0;
	}

}
