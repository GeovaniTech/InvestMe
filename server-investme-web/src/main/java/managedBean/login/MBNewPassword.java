package managedBean.login;

import java.util.ArrayList;
import java.util.List;

import abstracts.AbstractMBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.inject.Named;
import keep.client.IKeepClientSBean;
import utils.EncryptionUtil;
import utils.JWTUtil;
import utils.MessageUtil;
import utils.StringUtil;

@Named("MBNewPassword")
@SessionScoped
public class MBNewPassword extends AbstractMBean {

	private static final long serialVersionUID = 838212801483440031L;
	
	private String email;
	private String password;
	private String repeatedPassword;
	private String token;
	private List<String> blacklistTokens;
	private boolean validToken;
	
	@EJB
	private IKeepClientSBean clientSBean;
	
	public MBNewPassword() {
		this.setBlacklistTokens(new ArrayList<String>());
	}
	
	public void verifyToken() {
		if(!this.isTokenValid()) {
			this.setValidToken(false);
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_or_expired_token"), FacesMessage.SEVERITY_ERROR);
			
			return;
		}
		
		this.setValidToken(true);
	}
	
	public boolean isTokenValid() {
		if(StringUtil.isNotNull(this.getToken()) && !this.getBlacklistTokens().contains(this.getToken())) {
			String encryptedEmail = JWTUtil.getValueFromToken("newPassword", this.getToken());
			
			if(StringUtil.isNotNull(encryptedEmail)) {		
				this.setEmail(EncryptionUtil.decryptNormalText(encryptedEmail));
				return true;
			}
			
			return false;
		}
		
		return false;
	}
	
	public void saveChanges() {
		if(this.getPassword().equals(this.getRepeatedPassword())) {
			this.getClientSBean().setNewPassword(this.getEmail(), this.getPassword());
		} else {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("password_are_not_the_same"), FacesMessage.SEVERITY_WARN);
		}
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

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<String> getBlacklistTokens() {
		return blacklistTokens;
	}

	public void setBlacklistTokens(List<String> blacklistTokens) {
		this.blacklistTokens = blacklistTokens;
	}

	public boolean isValidToken() {
		return validToken;
	}

	public void setValidToken(boolean validToken) {
		this.validToken = validToken;
	}

	public IKeepClientSBean getClientSBean() {
		return clientSBean;
	}

	public void setClientSBean(IKeepClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}

}
