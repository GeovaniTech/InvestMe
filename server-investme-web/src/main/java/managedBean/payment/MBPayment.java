package managedBean.payment;

import java.util.List;

import org.primefaces.event.SelectEvent;

import abstracts.AbstractFilterMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.payment.IKeepPaymentSBean;
import to.payment.TOFilterPayment;
import to.payment.TOPayment;

@Named(MBPayment.MANAGED_BEAN_NAME)
@ViewScoped
public class MBPayment extends AbstractFilterMBean<TOPayment, TOFilterPayment> {

	private static final long serialVersionUID = 7093006273130417671L;
	public static final String MANAGED_BEAN_NAME = "MBPayment";
	
	private TOPayment payment;
	
	@EJB
	private IKeepPaymentSBean paymentSBean;
	
	public MBPayment() {
		this.searchResults();
	}
	
	@PostConstruct
	@Override
	public void init() {
		this.clearFilters();
		this.searchResults();
	}

	public void initPayment() {
		this.getMBPaymentInfo().initPayment();
	}
	
	@Override
	public List<TOPayment> getData(TOFilterPayment filter) {
		return this.getPaymentSBean().search(filter);
	}

	@Override
	public Integer getCount(TOFilterPayment filter) {
		return this.getPaymentSBean().getCount(filter);
	}

	@Override
	public void clearFilters() {
		this.setFilter(new TOFilterPayment());	
	}

	@Override
	public void onRowSelect(SelectEvent<TOPayment> event) {
		this.getMBPaymentInfo().editPayment(event.getObject());
	}
	
	public MBPaymentInfo getMBPaymentInfo() {
		return this.getMBean(MBPaymentInfo.MANAGED_BEAN_NAME);
	}

	public TOPayment getPayment() {
		return payment;
	}

	public void setPayment(TOPayment payment) {
		this.payment = payment;
	}

	public IKeepPaymentSBean getPaymentSBean() {
		return paymentSBean;
	}

	public void setPaymentSBean(IKeepPaymentSBean paymentSBean) {
		this.paymentSBean = paymentSBean;
	}
	
}
