package to.transaction;

import abstracts.AbstractTOFilter;
import to.TODateRangeFilter;
import to.TOInputFilter;
import to.TOInputNumberFilter;

public class TOFilterTransaction extends AbstractTOFilter {

	private static final long serialVersionUID = 1199959907542665089L;

	private TOInputFilter active;
	private TOInputNumberFilter price;
	private TODateRangeFilter datePurchase;
	private String type;
	private Integer idCategory;
	private Integer idPayment;
	
	public TOFilterTransaction() {
		this.setActive(new TOInputFilter());
		this.setDatePurchase(new TODateRangeFilter());
		this.setPrice(new TOInputNumberFilter());
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
	
}
