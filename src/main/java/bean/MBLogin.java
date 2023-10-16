package bean;


import jakarta.inject.Named;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import manter.client.ManterClient;
import utils.AbstractBean;
import utils.EmailUtil;
import utils.MessageUtil;

@Named("MBLogin")
@ViewScoped
public class MBLogin extends AbstractBean {
	private static final long serialVersionUID = 774892722297274579L;
	
	private String email;
	private String password;
	private Boolean rememberMe;
	private ManterClient manterClientSBean;
	
	public MBLogin() {
		this.setManterClientSBean(new ManterClient());
	}
	
	public void logar() {
		if(!EmailUtil.validateEmail(this.getEmail())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_email"), FacesMessage.SEVERITY_ERROR);
			return;
		}
		
		this.getManterClientSBean().logar(this.getEmail(), this.getPassword());
	}
	
	// Getters and Setters
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getRememberMe() {
		return rememberMe;
	}
	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
	public ManterClient getManterClientSBean() {
		return manterClientSBean;
	}
	public void setManterClientSBean(ManterClient manterClientSBean) {
		this.manterClientSBean = manterClientSBean;
	}

}
