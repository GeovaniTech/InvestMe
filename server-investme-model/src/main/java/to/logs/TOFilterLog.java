package to.logs;

import java.util.Date;

import abstracts.AbstractTOFilter;
import enums.EnumLogCategory;
import enums.EnumLogType;
import to.TODateRangeFilter;
import to.TOInputFilter;

public class TOFilterLog extends AbstractTOFilter {

	private static final long serialVersionUID = 4946823019523503363L;

	private TOInputFilter email;
	private TOInputFilter message;
	private TOInputFilter ip;
	private TODateRangeFilter date;
	private EnumLogCategory category;
	private EnumLogType type;
	
	public TOFilterLog() {
		this.setEmail(new TOInputFilter());
		this.setMessage(new TOInputFilter());
		this.setIp(new TOInputFilter());
		this.setDate(new TODateRangeFilter());
		this.getDate().setFrom(new Date());
	}
	
	// Getters and Setters
	public TOInputFilter getEmail() {
		return email;
	}
	public void setEmail(TOInputFilter email) {
		this.email = email;
	}
	public TOInputFilter getMessage() {
		return message;
	}
	public void setMessage(TOInputFilter message) {
		this.message = message;
	}
	public TOInputFilter getIp() {
		return ip;
	}
	public void setIp(TOInputFilter ip) {
		this.ip = ip;
	}
	public TODateRangeFilter getDate() {
		return date;
	}
	public void setDate(TODateRangeFilter date) {
		this.date = date;
	}
	public EnumLogCategory getCategory() {
		return category;
	}
	public void setCategory(EnumLogCategory category) {
		this.category = category;
	}
	public EnumLogType getType() {
		return type;
	}
	public void setType(EnumLogType type) {
		this.type = type;
	}

}
