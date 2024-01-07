package to.payment;

import abstracts.AbstractTOFilter;
import to.TOInputFilter;

public class TOFilterPayment extends AbstractTOFilter {

	private static final long serialVersionUID = -2353161411737930269L;
	
	private TOInputFilter name;

	public TOFilterPayment() {
		this.setName(new TOInputFilter());
	}
	
	public TOInputFilter getName() {
		return name;
	}

	public void setName(TOInputFilter name) {
		this.name = name;
	}
	
	
}
