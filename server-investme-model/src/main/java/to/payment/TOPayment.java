package to.payment;

import abstracts.AbstractTOObject;

public class TOPayment extends AbstractTOObject {

	private static final long serialVersionUID = 8157737976710047562L;

	private int id;
	private String name;
	private boolean installmentable;
	private Integer dueDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isInstallmentable() {
		return installmentable;
	}
	public void setInstallmentable(boolean installmentable) {
		this.installmentable = installmentable;
	}
	public Integer getDueDate() {
		return dueDate;
	}
	public void setDueDate(Integer dueDate) {
		this.dueDate = dueDate;
	}
}
