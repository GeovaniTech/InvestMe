package bean;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import manter.client.ManterClient;
import utils.AbstractBean;

@Named("MBRegister")
@ViewScoped
public class MBRegister extends AbstractBean {
	private static final long serialVersionUID = -6921252465658970503L;
	
	private String email;
	private String password;
	private String repeatPassword;
	private ManterClient manterClientSBean;
	
	public MBRegister() {
		this.setManterClientSBean(new ManterClient());
	}
	
	public void register() {

	}
	
	// Getters and Setters
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public ManterClient getManterClientSBean() {
		return manterClientSBean;
	}
	public void setManterClientSBean(ManterClient manterClientSBean) {
		this.manterClientSBean = manterClientSBean;
	}
	
}
