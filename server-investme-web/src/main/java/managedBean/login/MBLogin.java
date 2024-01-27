package managedBean.login;

import org.apache.commons.lang3.exception.ExceptionUtils;

import abstracts.AbstractMBean;
import enums.EnumLogCategory;
import enums.EnumLogType;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.client.IKeepClientSBean;
import managedBean.appconfigs.MBAppConfigs;
import to.logs.TOLog;
import utils.EmailUtil;
import utils.EncryptionUtil;
import utils.JWTUtil;
import utils.MessageUtil;
import utils.RedirectURL;
import utils.StringUtil;

@Named("MBLogin")
@ViewScoped
public class MBLogin extends AbstractMBean {
	
	private static final long serialVersionUID = -6951441926941734297L;
	
	private String email;
	private String password;
	private String messageParam;
	
	@EJB
	private IKeepClientSBean clientSBean;

	public void logar() {
		if(EmailUtil.isValidEmailPattern(this.getEmail())) {
			if(this.getClientSBean().logar(this.getEmail(), this.getPassword())) {
				this.getMBAppConfigs().configAppByUserPreferences();
				
				RedirectURL.redirectTo("/investme/client/home");
			}
			
			return;
		}
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_email"), FacesMessage.SEVERITY_ERROR);
	}
	
	public void showFinishedRegisterMessage() {
		if(StringUtil.isNotNull(this.getMessageParam())) {
			if(this.getMessageParam().equals("finished")) {
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("registration_completed_successfully"), FacesMessage.SEVERITY_INFO);
			} else if(this.getMessageParam().equals("newpassword")){
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("password_change_successfully"), FacesMessage.SEVERITY_INFO);
			} else if(this.getMessageParam().equals("userblocked")) {
				MessageUtil.sendMessage( MessageUtil.getMessageFromProperties("user_blocked"), FacesMessage.SEVERITY_ERROR);
			}
		}
	}
	
	public void sendEmailForgotPassoword() {
		String title = "InvestMe - Solicitação de Troca de Senha";
		
		StringBuilder description = new StringBuilder();
		
		description.append("<h2>Troca de Senha de acesso ao InvestMe<h2/>");
		description.append("<p>Olá,</p>");
		description.append("<p>Para trocar sua senha de acesso, entre no link abaixo e insira sua nova senha.</p>");
		description.append("<p><a href=https://www.devpree.com.br/investme/newpassword/");
		description.append(JWTUtil.generateToken("newPassword", EncryptionUtil.encryptNormalText(this.getEmail()))).append(">Troca de Senha</a><p/>");
		description.append("<p>Caso você não tenha solicitado a troca de senha no InvestMe ");
		description.append("ou acredite que este email tenha sido enviado por engano, por favor, desconsidere esta mensagem.</p>");
		description.append("Atenciosamente, <br>");
		description.append("A equipe InvestMe <br>");
		
		TOLog log = new TOLog();
		log.setCategory(EnumLogCategory.RECOVERY_EMAIL);
		log.setCreationUser(this.getEmail());
		log.setIp(this.getUserIpAddress());
		
		try {
			EmailUtil.sendMail(this.getEmail(), title, description.toString(), MessageUtil.getMessageFromProperties("email_new_password"));
			log.setType(EnumLogType.INFO);
			log.setStack("Email sent successfully. Credentials: " + this.getEmail());
		} catch (Exception e) {
			log.setStack(ExceptionUtils.getStackTrace(e));
			log.setType(EnumLogType.EXCEPTION);
		}
		
		saveLog(log);
	}
	
	public void validateSendNewPassword() {
		if(!EmailUtil.isValidEmailPattern(this.getEmail())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_email"), FacesMessage.SEVERITY_ERROR);
			return;
		}
		
		if(!this.getClientSBean().verifyClient(this.getEmail())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("user_not_found"), FacesMessage.SEVERITY_ERROR);
			
			TOLog log = new TOLog();
			
			log.setCreationUser(this.getEmail());
			log.setCategory(EnumLogCategory.INVALID_EMAIL_RECOVERY);
			log.setType(EnumLogType.WARN);
			log.setStack("Invalid email to send recovery password.");
			log.setIp(this.getRequest().getRemoteAddr());
			
			saveLog(log);
			return;
		}
		
		this.sendEmailForgotPassoword();
	}
	
	private MBAppConfigs getMBAppConfigs() {
		return this.getMBean(MBAppConfigs.MANAGED_BEAN_NAME);
	}
	
	// Getters and Setters
	public IKeepClientSBean getClientSBean() {
		return clientSBean;
	}

	public void setClientSBean(IKeepClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}

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

	public String getMessageParam() {
		return messageParam;
	}

	public void setMessageParam(String messageParam) {
		this.messageParam = messageParam;
	}

}
