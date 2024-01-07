package to;

import java.io.Serializable;

import jakarta.persistence.TemporalType;

public class TOParameter implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String paramName;
	private Object value;
	private TemporalType dateType;
	
	public TOParameter(String paramName, Object value) {
		super();
		this.paramName = paramName;
		this.value = value;
	}
	
	public TOParameter(String paramName, Object value, TemporalType dateType) {
		super();
		this.paramName = paramName;
		this.value = value;
		this.dateType = dateType;
	}

	// Getters and Setters
	public String getParamName() {
		return paramName;
	}
	
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}

	public TemporalType getDateType() {
		return dateType;
	}

	public void setDateType(TemporalType dateType) {
		this.dateType = dateType;
	}
	
}
