package to.client;

import abstracts.AbstractTOFilter;
import to.TODateRangeFilter;
import to.TOInputFilter;

public class TOFilterClient extends AbstractTOFilter {
	private static final long serialVersionUID = 3575492733459738282L;
	
	private int id;
	private TOInputFilter email;
	private String securityLevel;
	private Boolean blocked;
	private TOInputFilter phoneNumber;
	private TODateRangeFilter lastLogin;
	
	public TOFilterClient() {
		this.setEmail(new TOInputFilter());
		this.setPhoneNumber(new TOInputFilter());
		this.setLastLogin(new TODateRangeFilter());
	}
	
	// Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	public TODateRangeFilter getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(TODateRangeFilter lastLogin) {
		this.lastLogin = lastLogin;
	}
	public Boolean getBlocked() {
		return blocked;
	}
	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}
	public TOInputFilter getEmail() {
		return email;
	}
	public void setEmail(TOInputFilter email) {
		this.email = email;
	}
	public TOInputFilter getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(TOInputFilter phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
