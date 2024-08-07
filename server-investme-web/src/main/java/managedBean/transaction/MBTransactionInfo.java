package managedBean.transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.PrimeFaces;

import abstracts.AbstractMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.category.IKeepCategorySBean;
import keep.payment.IKeepPaymentSBean;
import keep.transaction.IKeepTransactionSBean;
import to.category.TOCategory;
import to.payment.TOPayment;
import to.transaction.TOTransaction;

@Named(MBTransactionInfo.MANAGED_BEAN_NAME)
@ViewScoped
public class MBTransactionInfo extends AbstractMBean {

	private static final long serialVersionUID = 2496483223164889240L;
	public static final String MANAGED_BEAN_NAME = "MBTransactionInfo";
	
	private TOTransaction transaction;
	private boolean editing;
	private Integer idCategorySelected;
	private Integer idPaymentSelected;
	private boolean continueEntering;
	private boolean investment;
	
	private List<TOCategory> categories;
	private List<TOPayment> payments;
	
	@EJB
	private IKeepTransactionSBean transactionSBean;

	@EJB
	private IKeepCategorySBean categorySBean;
	
	@EJB
	private IKeepPaymentSBean paymentSBean;
	
	@PostConstruct
	public void init() {
		this.setPayments(this.getPaymentSBean().listAll());
		this.setCategories(new ArrayList<TOCategory>());
		this.setContinueEntering(true);
	}
	
	public void initTransaction() {
		this.setTransaction(new TOTransaction());
		this.setContinueEntering(true);
		this.setEditing(false);
	}
	
	public void initInvestment() {
		this.setCategories(this.getCategorySBean().searchAllCategories("investment"));
		this.setInvestment(true);
		updateForm();
	}
	
	public void initExpense() {
		this.setCategories(this.getCategorySBean().searchAllCategories("expense"));
		this.setInvestment(false);
		updateForm();
	}
	
	public void save() {
		if(this.isValidFields()) {
			try {
				TOCategory category = this.getCategorySBean().findById(this.getIdCategorySelected());
				TOPayment payment = this.getPaymentSBean().findById(this.getIdPaymentSelected());
				
				this.getTransaction().setCreationDate(new Date());
				this.getTransaction().setCreationUser(this.getClientSession().getEmail());
				this.getTransaction().setCategory(category);
				this.getTransaction().setPayment(payment);
				this.getTransaction().setClient(this.getClientSession());
				
				this.getTransactionSBean().save(this.getTransaction());
				this.showMessageItemSaved(this.getTransaction().getActive());

				if(this.isContinueEntering()) {
					this.initTransaction();
				} else {
					this.setEditing(true);
				}
			} catch (Exception e) {
				showMessageError(e);
			}
		}
	}
	
	public void change() {		
		if(this.isValidFields()) {
			try {
				if(!this.getIdCategorySelected().equals(this.getTransaction().getCategory().getId())) {
					TOCategory category = this.getCategorySBean().findById(this.getIdCategorySelected());
					this.getTransaction().setCategory(category);
				}
				
				if(!this.getIdPaymentSelected().equals(this.getTransaction().getPayment().getId())) {
					TOPayment payment = this.getPaymentSBean().findById(this.getIdPaymentSelected());
					this.getTransaction().setPayment(payment);
				}
				
				this.getTransaction().setChangeDate(new Date());
				this.getTransaction().setChangeUser(this.getClientSession().getEmail());
				this.getTransaction().setClient(this.getClientSession());
				
				this.getTransactionSBean().change(this.getTransaction());
				this.showMessageItemChanged(this.getTransaction().getActive());
				this.setEditing(true);
			} catch (Exception e) {
				showMessageError(e);
			}
		}
	}
	
	public void remove() {
		try {
			this.getTransactionSBean().remove(this.getTransaction());
			showMessageItemRemoved(this.getTransaction().getActive());
			
			this.setEditing(false);
			this.initTransaction();
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public boolean isValidFields() {
		if(this.getIdCategorySelected() == null) {
			this.addMessage("msg_categories_not_found", FacesMessage.SEVERITY_WARN);
			return false;
		}
		
		if(this.getIdPaymentSelected() == null) {
			this.addMessage("msg_payments_not_found", FacesMessage.SEVERITY_WARN);
			return false;
		}

		return true;
	}
	
	public void editTransaction(TOTransaction transaction) {
		this.setTransaction(transaction);
		this.setEditing(true);
		this.setContinueEntering(false);
		
		this.setIdCategorySelected(transaction.getCategory().getId());
		this.setIdPaymentSelected(transaction.getPayment().getId());
		this.setInvestment(transaction.getCategory().getType().equals("investment"));
		
		this.listAllCategories();
		this.updateForm();
	}
	
	public void listAllCategories() {
		this.setCategories(new ArrayList<TOCategory>());
		this.getCategories().addAll(this.getCategorySBean().searchAllCategories(null));
	}
	
	public void updateForm() {
		PrimeFaces.current().ajax().update("dialogTransactionInfo:formTransactionInfo");
	}
	
	public TOTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(TOTransaction transaction) {
		this.transaction = transaction;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
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

	public IKeepCategorySBean getCategorySBean() {
		return categorySBean;
	}

	public void setCategorySBean(IKeepCategorySBean categorySBean) {
		this.categorySBean = categorySBean;
	}

	public Integer getIdCategorySelected() {
		return idCategorySelected;
	}

	public void setIdCategorySelected(Integer idCategorySelected) {
		this.idCategorySelected = idCategorySelected;
	}

	public Integer getIdPaymentSelected() {
		return idPaymentSelected;
	}

	public void setIdPaymentSelected(Integer idPaymentSelected) {
		this.idPaymentSelected = idPaymentSelected;
	}

	public List<TOPayment> getPayments() {
		return payments;
	}

	public void setPayments(List<TOPayment> payments) {
		this.payments = payments;
	}

	public IKeepPaymentSBean getPaymentSBean() {
		return paymentSBean;
	}

	public void setPaymentSBean(IKeepPaymentSBean paymentSBean) {
		this.paymentSBean = paymentSBean;
	}

	public boolean isContinueEntering() {
		return continueEntering;
	}

	public void setContinueEntering(boolean continueEntering) {
		this.continueEntering = continueEntering;
	}

	public boolean isInvestment() {
		return investment;
	}

	public void setInvestment(boolean investment) {
		this.investment = investment;
	}
}
