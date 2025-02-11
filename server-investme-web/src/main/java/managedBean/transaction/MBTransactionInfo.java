package managedBean.transaction;

import java.util.ArrayList;
import java.util.Calendar;
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
	private Integer idCategorySelected;
	private Integer idPaymentSelected;
	
	private boolean continueEntering;
	private boolean investment;
	private boolean editing;
	private boolean recurringPurchase;
	
	private List<TOCategory> categories;
	private List<TOPayment> payments;
	private List<Integer> installments;
	
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
		this.loadIntallments();
		this.setContinueEntering(true);
		this.setRecurringPurchase(false);
	}
	
	public void initTransaction() {
		this.setTransaction(new TOTransaction());
		this.setContinueEntering(true);
		this.setEditing(false);
	}
	
	public void initInvestment() {
		this.setCategories(this.getCategorySBean().searchAllCategories("investment"));
		this.setInvestment(true);
		this.setRecurringPurchase(false);
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
				
				int installments = 1;
				double pricePerTransaction = this.getTransaction().getPrice();
				
				Calendar purchaseDate = Calendar.getInstance();
				purchaseDate.setTime(this.getTransaction().getDatePurchase());

				if (payment.isInstallmentable() || this.isRecurringPurchase()) {					
					this.getTransaction().setAmount(1);
					installments = this.getTransaction().getInstallments();
					
					if (!this.isRecurringPurchase() && payment.isInstallmentable()) {
						pricePerTransaction = pricePerTransaction / installments;
						purchaseDate.add(Calendar.MONTH, purchaseDate.get(Calendar.DAY_OF_MONTH) > payment.getDueDate() ? 1 : 0);
						purchaseDate.set(Calendar.DAY_OF_MONTH, payment.getDueDate());
					}
				}
				
				for (int i = 0; i < installments; i++) {
					TOTransaction transaction = (TOTransaction) this.getTransaction().clone();
					transaction.setCreationDate(new Date());
					transaction.setCreationUser(this.getClientSession().getEmail());
					transaction.setCategory(category);
					transaction.setPayment(payment);
					transaction.setClient(this.getClientSession());
					transaction.setDatePurchase(purchaseDate.getTime());
					transaction.setPaid((!payment.isInstallmentable() && !this.isRecurringPurchase()) || this.isInvestment() ? true : false);
					transaction.setPrice(pricePerTransaction);
					
					this.getTransactionSBean().save(transaction);
					purchaseDate.add(Calendar.MONTH, 1);
				}
				
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
	
	public boolean isRenderNumberInstallments() {
		if (!this.isEditing() && !this.isInvestment() && this.getTransaction() != null) {
			TOPayment payment = null;
			
			if (this.getTransaction().getPayment() != null) {
				payment = this.getTransaction().getPayment();
			} else if (this.getIdPaymentSelected() != null) {
				payment = this.getPaymentSBean().findById(this.getIdPaymentSelected());
			} else {
				payment = this.getPayments().isEmpty() ? null : this.getPayments().get(0);
			}
			
			this.getTransaction().setPayment(payment);

			return payment != null && payment.isInstallmentable();
		}

		return false;
	}
	
	public void updateAmountWithPayment() {
		if (this.getIdPaymentSelected() != null) {
			TOPayment payment = this.getPaymentSBean().findById(this.getIdPaymentSelected());
			this.getTransaction().setPayment(payment);
			
			if (payment.isInstallmentable() && !this.isInvestment()) {
				this.getTransaction().setAmount(1);
			}
		} 
	}
	
	private void loadIntallments() {
		this.setInstallments(new ArrayList<Integer>());
		for (int i = 1; i <= 72; i++) {
			this.getInstallments().add(i);
		}
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

	public List<Integer> getInstallments() {
		return installments;
	}

	public void setInstallments(List<Integer> installments) {
		this.installments = installments;
	}

	public boolean isRecurringPurchase() {
		return recurringPurchase;
	}

	public void setRecurringPurchase(boolean recurringPurchase) {
		this.recurringPurchase = recurringPurchase;
	}
}
