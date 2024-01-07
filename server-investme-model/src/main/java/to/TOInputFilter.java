package to;

import java.io.Serializable;

public class TOInputFilter implements Serializable {
	private static final long serialVersionUID = 1385117733291072620L;
	
	private String value;
	private String type;
	
	// Getters and Setters
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
