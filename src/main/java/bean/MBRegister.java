package bean;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import manter.register.ManterRegister;
import utils.AbstractBean;

@Named("MBRegister")
@ViewScoped
public class MBRegister extends AbstractBean {
	private static final long serialVersionUID = -6921252465658970503L;
	
	private String nameUser;
	private String password;
	private String repeatPassword;
	private ManterRegister manterRegister;
	
	public MBRegister() {
		this.manterRegister = new ManterRegister();
	}
	
	public void register() {
		if(nameUser != null
			&& !nameUser.equals("")
			&& password != null
			&& !password.equals("")
			&& repeatPassword != null
			&& !repeatPassword.equals("")) {
			
			this.getManterRegister().register(nameUser, password, repeatPassword);
		}
	}
	
	public String getNameUser() {
		return nameUser;
	}
	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepeatPassword() {
		return repeatPassword;
	}
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	public ManterRegister getManterRegister() {
		return manterRegister;
	}
	public void setManterRegister(ManterRegister manterRegister) {
		this.manterRegister = manterRegister;
	}
}
