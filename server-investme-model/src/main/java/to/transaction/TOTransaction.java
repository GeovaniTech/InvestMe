package to.transaction;

import java.util.Date;
import java.util.List;

import abstracts.AbstractTOObject;
import to.category.TOCategory;
import to.client.TOClient;
import to.installment.TOInstallment;
import to.payment.TOPayment;

public class TOTransaction extends AbstractTOObject implements Cloneable {

	private static final long serialVersionUID = -1384422120365897196L;
	
	private int id;
	private String active;
	private Double price;
	private Integer amount;
	private Date datePurchase;
	private TOCategory category;
	private TOPayment payment;
	private TOClient client;
	private boolean paid;
	private boolean notify;
	
	private List<TOInstallment> installments;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Date getDatePurchase() {
		return datePurchase;
	}
	public void setDatePurchase(Date datePurchase) {
		this.datePurchase = datePurchase;
	}
	public TOCategory getCategory() {
		return category;
	}
	public void setCategory(TOCategory category) {
		this.category = category;
	}
	public TOPayment getPayment() {
		return payment;
	}
	public void setPayment(TOPayment payment) {
		this.payment = payment;
	}
	public TOClient getClient() {
		return client;
	}
	public void setClient(TOClient client) {
		this.client = client;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public List<TOInstallment> getInstallments() {
		return installments;
	}
	public void setInstallments(List<TOInstallment> installments) {
		this.installments = installments;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public boolean isNotify() {
		return notify;
	}
	public void setNotify(boolean notify) {
		this.notify = notify;
	}
}
