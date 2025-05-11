package keep.transaction;

import java.util.ArrayList;
import java.util.List;

import abstracts.AbstractKeep;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Tuple;
import keep.installment.IKeepInstallmentSBean;
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

	@EJB
	private IKeepInstallmentSBean installmentSBean;
	
	public KeepTransactionSBean() {
		super(Transaction.class, TOTransaction.class);
	}

	@Override
	public void save(TOTransaction transaction) {
		Transaction model = this.convertToModel(transaction);
		
		this.getEntityManager().persist(model);
		this.getEntityManager().flush();
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
			sql.append(" AND T.payment.id = :idPayment ");
			params.add(new TOParameter("idPayment", filter.getIdPayment()));
		}
		
		if (filter.getPaid() != null) {
			sql.append(" AND T.paid = :paid ");
			params.add(new TOParameter("paid", filter.getPaid()));
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getYearsFromTransaction() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT DISTINCT YEAR(T.datePurchase) ")
			.append(this.getFromTransactions())
			.append(" WHERE T.client.id = :clientId ")
			.append(" ORDER BY YEAR(T.datePurchase) DESC ");
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("clientId", this.getClientSession().getId());
		
		return query.getResultList();
	}

	@Override
	public Double getTotalExpensesChartByYear(Integer year, Integer month) {
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT SUM(T.amount * T.price) ")
			.append(this.getFromTransactions())
			.append(" WHERE T.client.id = :clientId ")
			.append(" AND T.category.type = 'expense' ")
			.append(" AND YEAR(T.datePurchase) = :year ")
			.append(" AND MONTH(T.datePurchase) = :month ")
			.append(" GROUP BY MONTH(T.datePurchase) ");

		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("clientId", this.getClientSession().getId());
		query.setParameter("year", year);
		query.setParameter("month", month);
		
		if(query.getResultList().size() > 0) {
			Number number = (Number) query.getSingleResult();
			
			return number.doubleValue();
		}
		
		return 0.0;
	}
	
	@Override
	public Double getTotalExpectedExpensesChartByYear(Integer year, Integer month) {
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT SUM(T.amount * T.price) ")
			.append(this.getFromTransactions())
			.append(" WHERE T.client.id = :clientId ")
			.append(" AND T.category.type = 'expense' ")
			.append(" AND YEAR(T.datePurchase) = :year ")
			.append(" AND MONTH(T.datePurchase) = :month ")
			.append(" AND T.paid = false ")
			.append(" GROUP BY MONTH(T.datePurchase) ");

		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("clientId", this.getClientSession().getId());
		query.setParameter("year", year);
		query.setParameter("month", month);
		
		if(query.getResultList().size() > 0) {
			Number number = (Number) query.getSingleResult();
			
			return number.doubleValue();
		}
		
		return 0.0;
	}

	@Override
	public Double getTotalInvestmentsChartByYear(Integer year, Integer month) {
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT SUM(T.amount * T.price) ")
			.append(this.getFromTransactions())
			.append(" WHERE T.client.id = :clientId ")
			.append(" AND T.category.type = 'investment' ")
			.append(" AND YEAR(T.datePurchase) = :year ")
			.append(" AND MONTH(T.datePurchase) = :month ")
			.append(" GROUP BY MONTH(T.datePurchase) ");

		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("clientId", this.getClientSession().getId());
		query.setParameter("year", year);
		query.setParameter("month", month);
		
		if(query.getResultList().size() > 0) {
			Number number = (Number) query.getSingleResult();
			
			return number.doubleValue();
		}
		
		return 0.0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Number> getTotalByCategoryChartInvestment(TOFilterTransaction filter) {		
		StringBuilder sql = new StringBuilder();
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT SUM(T.price * T.amount) ")
			.append(this.getFromTransactions())
			.append(" RIGHT JOIN T.category category ")
			.append(this.getWhereTransactions(filter, params))
			.append(" GROUP BY category.name ")
			.append(" ORDER BY category.name ");
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Number.class);
		setParameters(query, params);
		
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCategoriesNameWithTransactions(TOFilterTransaction filter) {
		StringBuilder sql = new StringBuilder();
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT T.category.name FROM ");
		sql.append(Transaction.class.getSimpleName()).append(" T ");
		sql.append(this.getWhereTransactions(filter, params));
		sql.append(" GROUP BY T.category.name ");
		sql.append(" ORDER BY T.category.name");
		
		Query query = this.getEntityManager().createQuery(sql.toString(), String.class);
		setParameters(query, params);
		
		return query.getResultList();
	}

	@Override
	public void deleteTransactionsFromUser() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE ").append(this.getFromTransactions());
		sql.append(" WHERE T.client.email = :email");
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("email", this.getClientSession().getEmail());
		
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPaymentsNameWithTransactions(TOFilterTransaction filter) {
		StringBuilder sql = new StringBuilder();
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT T.payment.name FROM ");
		sql.append(Transaction.class.getSimpleName()).append(" T ");
		sql.append(this.getWhereTransactions(filter, params));
		sql.append(" GROUP BY T.payment.name ");
		sql.append(" ORDER BY T.payment.name");
		
		Query query = this.getEntityManager().createQuery(sql.toString(), String.class);
		setParameters(query, params);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Number> getTotalByPayment(TOFilterTransaction filter) {
		StringBuilder sql = new StringBuilder();
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT SUM(T.price * T.amount) ")
			.append(this.getFromTransactions())
			.append(" RIGHT JOIN T.payment payment ")
			.append(this.getWhereTransactions(filter, params))
			.append(" GROUP BY payment.name ")
			.append(" ORDER BY payment.name ");
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Number.class);
		setParameters(query, params);
		
		return query.getResultList();
	}

	@Override
	public Double getTotalValueByPayment(TOFilterTransaction filter) {
		StringBuilder sql = new StringBuilder();
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT SUM(T.price * T.amount) ")
			.append(this.getFromTransactions())
			.append(this.getWhereTransactions(filter, params));
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		setParameters(query, params);
		
		Number value = (Number) query.getSingleResult();
		
		return value != null ? value.doubleValue() : 0.0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TOTransaction> listPendingPaymentTransactions() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT t.active as active, t.creationuser as creationUser ")
			.append(" FROM transaction t ")
			.append(" INNER JOIN payment p ON p.id = t.payment_id ")
			.append(" WHERE t.paid = false ")
			.append(" AND p.duedate IS NOT NULL ")
			.append(" AND t.notify = true ")
			.append(" AND DATE((date_trunc('month', t.datepurchase) + p.duedate * INTERVAL '1 day' - INTERVAL '1 day')) = DATE((CURRENT_DATE + INTERVAL '1 day'))");
	
		Query query = this.getEntityManager().createNativeQuery(sql.toString(), Tuple.class);
		List<TOTransaction> transactions = new ArrayList<TOTransaction>();
		
		for (Tuple transaction : (List<Tuple>) query.getResultList()) {
			TOTransaction to = new TOTransaction();
			
			to.setActive((String) transaction.get(0));;
			to.setCreationUser((String) transaction.get(1));
			
			transactions.add(to);
		}
		
		return transactions;
	}

	public IKeepInstallmentSBean getInstallmentSBean() {
		return installmentSBean;
	}

	public void setInstallmentSBean(IKeepInstallmentSBean installmentSBean) {
		this.installmentSBean = installmentSBean;
	}
}
