package managedBean.payment;

import java.util.Date;

import org.primefaces.PrimeFaces;

import abstracts.AbstractMBean;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.payment.IKeepPaymentSBean;
import to.payment.TOPayment;

@Named(MBPaymentInfo.MANAGED_BEAN_NAME)
@ViewScoped
public class MBPaymentInfo extends AbstractMBean {

	private static final long serialVersionUID = -209387974542831500L;
	public static final String MANAGED_BEAN_NAME = "MBPaymentInfo";
	
	private TOPayment payment;
	private boolean editing;
	
	@EJB
	private IKeepPaymentSBean paymentSBean;
	
	public MBPaymentInfo() {
		this.initPayment();
	}
	
	public void save() {
		try {
			this.getPayment().setCreationDate(new Date());
			this.getPayment().setCreationUser(this.getClientSession().getEmail());
			
			this.getPaymentSBean().save(this.getPayment());
			this.setEditing(true);
			showMessageItemSaved(this.getPayment().getName());
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public void change() {
		try {
			this.getPayment().setChangeDate(new Date());
			this.getPayment().setChangeUser(this.getClientSession().getEmail());

			this.getPaymentSBean().change(this.getPayment());
			showMessageItemChanged(this.getPayment().getName());
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public void remove() {
		try {
			this.getPaymentSBean().remove(this.getPayment());
			showMessageItemRemoved(this.getPayment().getName());
			
			this.initPayment();
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public void initPayment() {
		this.setEditing(false);
		this.setPayment(new TOPayment());
		
		this.updateFormPayment();
	}
	
	public void editPayment(TOPayment payment) {
		this.setEditing(true);
		this.setPayment(payment);
		
		this.updateFormPayment();
	}
	
	public void updateFormPayment() {
		PrimeFaces.current().ajax().update("dialogPaymentInfo:formPaymentInfo");
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
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
