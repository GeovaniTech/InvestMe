package to.category;

import abstracts.AbstractTOFilter;
import to.TOInputFilter;

public class TOFilterCategory extends AbstractTOFilter {

	private static final long serialVersionUID = -3106096019174548210L;

	private TOInputFilter name;
	private TOInputFilter description;
	private TOInputFilter icon;
	
	public TOFilterCategory() {
		this.setName(new TOInputFilter());
		this.setDescription(new TOInputFilter());
		this.setIcon(new TOInputFilter());
	}
	
	public TOInputFilter getName() {
		return name;
	}
	public void setName(TOInputFilter name) {
		this.name = name;
	}
	public TOInputFilter getDescription() {
		return description;
	}
	public void setDescription(TOInputFilter description) {
		this.description = description;
	}
	public TOInputFilter getIcon() {
		return icon;
	}
	public void setIcon(TOInputFilter icon) {
		this.icon = icon;
	}
		
}
