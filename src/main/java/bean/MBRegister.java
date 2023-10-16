package bean;

import jakarta.inject.Named;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import manter.client.ManterClient;
import utils.AbstractBean;
import utils.EmailUtil;
import utils.MessageUtil;

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
		if(!EmailUtil.validateEmail(this.getEmail())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_email"), FacesMessage.SEVERITY_ERROR);
			return;
		}
		
		if(!this.getPassword().equals(this.getRepeatPassword())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("password_are_not_the_same"), FacesMessage.SEVERITY_ERROR);
			return;
		}
		
		if(this.getManterClientSBean().verifyIfExistsUserByEmail(this.getEmail())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("existing_email"), FacesMessage.SEVERITY_ERROR);
			return;
		}
		
		this.getManterClientSBean().register(this.getEmail(), this.getPassword());
		
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
