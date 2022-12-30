package bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import manter.ManterUser;
import to.TOUser;

@Named("MBUser")
@ViewScoped
public class MBUser implements Serializable {
	private static final long serialVersionUID = -8527177710843576826L;
	
	private TOUser user;
	private ManterUser manterUser;
	
	public MBUser() {
		this.user = new TOUser();
		this.manterUser = new ManterUser();
	}

	public void validateAccess() {
		
	}
	
	public TOUser getUser() {
		return user;
	}

	public void setUser(TOUser user) {
		this.user = user;
	}

	public ManterUser getManterUser() {
		return manterUser;
	}

	public void setManterUser(ManterUser manterUser) {
		this.manterUser = manterUser;
	}
}
