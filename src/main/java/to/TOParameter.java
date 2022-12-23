package to;

public class TOParameter {
	private String parameter;
	private Object value;

	public TOParameter(String parameter, Object value) {
		super();
		this.parameter = parameter;
		this.value = value;
	}
	
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}
