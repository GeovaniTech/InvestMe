package managedBean.home;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.event.SelectEvent;

import abstracts.AbstractFilterMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.category.IKeepCategorySBean;
import keep.payment.IKeepPaymentSBean;
import keep.transaction.IKeepTransactionSBean;
import managedBean.expense.MBChartExpenditures;
import managedBean.investment.MBChartInvestments;
import managedBean.transaction.MBTransactionInfo;
import to.category.TOCategory;
import to.payment.TOPayment;
import to.transaction.TOFilterTransaction;
import to.transaction.TOTransaction;

@Named(MBHome.MANAGED_BEAN_NAME)
@ViewScoped
public class MBHome extends AbstractFilterMBean<TOTransaction, TOFilterTransaction> {
	
	private static final long serialVersionUID = 8184287297629627293L;
	public static final String MANAGED_BEAN_NAME = "MBHome";
	
	private TOTransaction transaction;
	
	private List<TOCategory> categories;
	private List<TOPayment> payments;
	
	@EJB
	private IKeepTransactionSBean transactionSBean;
	
	@EJB
	private IKeepCategorySBean categorySBean;
	
	@EJB
	private IKeepPaymentSBean paymentsBean;
	
	@PostConstruct
	@Override
	public void init() {
		this.setCategories(new ArrayList<TOCategory>());
		this.getCategories().addAll(this.getCategorySBean().searchCategoriesByType("investment"));
		this.getCategories().addAll(this.getCategorySBean().searchCategoriesByType("expense"));
		
		this.setPayments(this.getPaymentsBean().listAll());
		
		this.clearFilters();
		this.searchResults();
	}

	@Override
	public List<TOTransaction> getData(TOFilterTransaction filter) {
		return this.getTransactionSBean().search(filter);
	}

	@Override
	public Integer getCount(TOFilterTransaction filter) {
		return this.getTransactionSBean().getCount(filter);
	}

	@Override
	public void clearFilters() {
		this.setFilter(new TOFilterTransaction());
	}

	@Override
	public void onRowSelect(SelectEvent<TOTransaction> event) {
		this.getMBTransactionInfo().editTransaction(event.getObject());
	}
	
	public void initTransaction() {
		this.getMBTransactionInfo().initTransactionFromHome();
	}
	
	public Double getTotalExpense() {
		return this.getTransactionSBean().getTotalByType(this.getFilter(), "expense");
	}
	
	public Double getTotalInvestment() {
		return this.getTransactionSBean().getTotalByType(this.getFilter(), "investment");
	}
	
	public MBTransactionInfo getMBTransactionInfo() {
		return this.getMBean(MBTransactionInfo.MANAGED_BEAN_NAME);
	}
	
	public MBChartInvestments getMBChartInvestments() {
		return this.getMBean(MBChartInvestments.MANAGED_BEAN_NAME);
	}
	
	public MBChartExpenditures getMBChartExpenditures() {
		return this.getMBean(MBChartExpenditures.MANAGED_BEAN_NAME);
	}

	public IKeepTransactionSBean getTransactionSBean() {
		return transactionSBean;
	}

	public void setTransactionSBean(IKeepTransactionSBean transactionSBean) {
		this.transactionSBean = transactionSBean;
	}

	public TOTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(TOTransaction transaction) {
		this.transaction = transaction;
	}

	public List<TOCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<TOCategory> categories) {
		this.categories = categories;
	}

	public List<TOPayment> getPayments() {
		return payments;
	}

	public void setPayments(List<TOPayment> payments) {
		this.payments = payments;
	}

	public IKeepCategorySBean getCategorySBean() {
		return categorySBean;
	}

	public void setCategorySBean(IKeepCategorySBean categorySBean) {
		this.categorySBean = categorySBean;
	}

	public IKeepPaymentSBean getPaymentsBean() {
		return paymentsBean;
	}

	public void setPaymentsBean(IKeepPaymentSBean paymentsBean) {
		this.paymentsBean = paymentsBean;
	}
	
}
