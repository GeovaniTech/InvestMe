package managedBean.invetment;

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
import managedBean.transaction.MBTransactionInfo;
import to.category.TOCategory;
import to.payment.TOPayment;
import to.transaction.TOFilterTransaction;
import to.transaction.TOTransaction;

@Named(MBInvestment.MANAGED_BEAN_NAME)
@ViewScoped
public class MBInvestment extends AbstractFilterMBean<TOTransaction, TOFilterTransaction> {
	
	private static final long serialVersionUID = 8184287297629627293L;
	public static final String MANAGED_BEAN_NAME = "MBInvestment";
	
	private List<TOCategory> categories;
	private List<TOPayment> payments;
	private TOTransaction investment;
	
	@EJB
	private IKeepCategorySBean categorySBean;
	
	@EJB
	private IKeepTransactionSBean transactionSBean;
	
	@EJB
	private IKeepPaymentSBean paymentsBean;
	
	@PostConstruct
	@Override
	public void init() {
		this.clearFilters();
		this.setCategories(this.getCategorySBean().searchCategoriesByType("investment"));
		this.setPayments(this.getPaymentsBean().listAll());
		this.searchResults();
	}
	
	public Double getTotalSpentByCategory(TOCategory category) {
		return this.getTransactionSBean().getTotalSpentByCategory(this.getFilter(), category);
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
		this.getFilter().setType("investment");
	}

	@Override
	public void onRowSelect(SelectEvent<TOTransaction> event) {
		this.getMBTransactionInfo().editInvestment(event.getObject());
	}
	
	public void initInvestment() {
		this.getMBTransactionInfo().initInvestment();
	}
	
	public MBTransactionInfo getMBTransactionInfo() {
		return this.getMBean(MBTransactionInfo.MANAGED_BEAN_NAME);
	}

	public IKeepCategorySBean getCategorySBean() {
		return categorySBean;
	}

	public void setCategorySBean(IKeepCategorySBean categorySBean) {
		this.categorySBean = categorySBean;
	}

	public IKeepTransactionSBean getTransactionSBean() {
		return transactionSBean;
	}

	public void setTransactionSBean(IKeepTransactionSBean transactionSBean) {
		this.transactionSBean = transactionSBean;
	}

	public List<TOCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<TOCategory> categories) {
		this.categories = categories;
	}

	public TOTransaction getInvestment() {
		return investment;
	}

	public void setInvestment(TOTransaction investment) {
		this.investment = investment;
	}

	public List<TOPayment> getPayments() {
		return payments;
	}

	public void setPayments(List<TOPayment> payments) {
		this.payments = payments;
	}

	public IKeepPaymentSBean getPaymentsBean() {
		return paymentsBean;
	}

	public void setPaymentsBean(IKeepPaymentSBean paymentsBean) {
		this.paymentsBean = paymentsBean;
	}

}
