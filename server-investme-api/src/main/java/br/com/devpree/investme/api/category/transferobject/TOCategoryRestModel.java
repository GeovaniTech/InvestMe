package br.com.devpree.investme.api.category.transferobject;

import br.com.devpree.investme.api.common.AbstractTOObject;

public class TOCategoryRestModel extends AbstractTOObject {

	private static final long serialVersionUID = 7157473484193202300L;
	
	private String name;
	private String type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
