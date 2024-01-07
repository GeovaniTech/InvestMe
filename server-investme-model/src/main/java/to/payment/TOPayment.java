package to.payment;

import abstracts.AbstractTOObject;

public class TOPayment extends AbstractTOObject {

	private static final long serialVersionUID = 8157737976710047562L;

	private int id;
	private String name;
	private String icon;
	
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

}
