package br.com.devpree.investme.api.payments.transferobject;

import br.com.devpree.investme.api.common.AbstractTOObject;

public class TOPaymentRestModel extends AbstractTOObject {

	private static final long serialVersionUID = 7157473484193202300L;
	
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
