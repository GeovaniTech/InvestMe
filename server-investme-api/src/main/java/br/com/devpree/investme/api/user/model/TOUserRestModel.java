package br.com.devpree.investme.api.user.model;

import java.util.Date;

import br.com.devpree.investme.api.common.AbstractTOObject;

public class TOUserRestModel extends AbstractTOObject {

	private static final long serialVersionUID = -1438969949448973721L;
	
	private String email;
	private String securityLevel;
	private Date lastLogin;
	private boolean blocked;
	private String phoneNumber;
	private boolean changePassword;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public boolean isChangePassword() {
		return changePassword;
	}
	public void setChangePassword(boolean changePassword) {
		this.changePassword = changePassword;
	}
	
}
