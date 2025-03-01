package to.transaction;

import java.util.Calendar;
import java.util.Date;

import abstracts.AbstractTOFilter;
import to.TODateRangeFilter;
import to.TOInputFilter;
import to.TOInputNumberFilter;

public class TOFilterTransaction extends AbstractTOFilter implements Cloneable {

	private static final long serialVersionUID = 1199959907542665089L;

	private TOInputFilter active;
	private TOInputNumberFilter price;
	private TODateRangeFilter datePurchase;
	private String type;
	private Boolean paid;
	private Integer idCategory;
	private Integer idPayment;
	
	public TOFilterTransaction() {
		this.setActive(new TOInputFilter());
		this.setDatePurchase(new TODateRangeFilter());
		this.setPrice(new TOInputNumberFilter());
        
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        this.getDatePurchase().setFrom(cal.getTime());
	}
	
	public TOInputFilter getActive() {
		return active;
	}
	public void setActive(TOInputFilter active) {
		this.active = active;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public TODateRangeFilter getDatePurchase() {
		return datePurchase;
	}
	public void setDatePurchase(TODateRangeFilter datePurchase) {
		this.datePurchase = datePurchase;
	}
	public TOInputNumberFilter getPrice() {
		return price;
	}
	public void setPrice(TOInputNumberFilter price) {
		this.price = price;
	}

	public Integer getIdCategory() {
		return idCategory;
	}
	public void setIdCategory(Integer idCategory) {
		this.idCategory = idCategory;
	}
	public Integer getIdPayment() {
		return idPayment;
	}
	public void setIdPayment(Integer idPayment) {
		this.idPayment = idPayment;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
