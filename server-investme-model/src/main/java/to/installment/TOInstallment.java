package to.installment;

import java.io.Serializable;
import java.util.Date;

import to.transaction.TOTransaction;

public class TOInstallment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private TOTransaction transaction;
	private Date referenceDate;
	private Double value;
	private Boolean paid;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TOTransaction getTransaction() {
		return transaction;
	}
	public void setTransaction(TOTransaction transaction) {
		this.transaction = transaction;
	}
	public Date getReferenceDate() {
		return referenceDate;
	}
	public void setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Boolean getPaid() {
		return paid;
	}
	public void setPaid(Boolean paid) {
		this.paid = paid;
	}	
}
