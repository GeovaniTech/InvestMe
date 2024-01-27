package managedBean.client;

import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;

import abstracts.AbstractMBean;
import enums.EnumLogCategory;
import enums.EnumLogType;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.client.IKeepClientSBean;
import to.client.TOClient;
import to.logs.TOLog;
import utils.EmailUtil;
import utils.MessageUtil;
import utils.RedirectURL;

@Named("MBClientInfo")
@ViewScoped
public class MBClientInfo extends AbstractMBean {
	
	private static final long serialVersionUID = 9183706213942034831L;
	
	private TOClient client;
	private boolean editing;
	
	@EJB
	private IKeepClientSBean clientSBean;
	
	public void save() {
		try {
			if(this.isEmailUserValid()) {
				this.getClient().setCreationUser(this.getClientSession().getEmail());
				this.getClient().setCreationDate(new Date());
				
				this.getClientSBean().save(this.getClient());
				this.setEditing(true);
				
				this.setClient(this.getClientSBean().findByEmail(this.getClient().getEmail()));
				
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_client_saved"), FacesMessage.SEVERITY_INFO);
			}
		} catch (Exception e) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_error_saving_client"), FacesMessage.SEVERITY_ERROR);
			e.printStackTrace();
		}	
	}
	
	public void change() {
		try {
			if(this.isEmailUserValid()) {
				this.getClient().setChangeUser(this.getClientSession().getEmail());
				this.getClient().setChangeDate(new Date());
				
				this.getClientSBean().change(this.getClient());
				
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_client_edited"), FacesMessage.SEVERITY_INFO);
			}
		} catch (Exception e) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_error_editing_client"), FacesMessage.SEVERITY_ERROR);
			e.printStackTrace();
		}
	}
	
	public boolean isEmailUserValid() {
		if(!EmailUtil.isValidEmailPattern(this.getClient().getEmail())) {
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("invalid_email"), FacesMessage.SEVERITY_ERROR);
			return false;
		}
		
		if(this.getClientSBean().existsClientByEmail(this.getClient().getEmail(), this.getClient().getId())) {

			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("existing_email"), FacesMessage.SEVERITY_ERROR);
			return false;
		}
		
		return true;
	}
	
	public void sendWelcomeEmailWithPassword() {
		StringBuilder description = new StringBuilder();
		
		description.append("<h2>Bem-vindo(a) ao InvestMe!</h2>");
		description.append("<p>Olá,	</p>");
		description.append("<p>Agradecemos por se cadastrar no InvestMe! ");
		description.append("Estamos felizes em tê-lo(a) como nosso(a) cliente.</p>");
		description.append("<p>Seu cadastro foi realizado manualmente. Para o seu primeiro acesso à plataforma, utilize o seu e-mail e a senha temporária 123. Após o primeiro login, recomendamos que escolha uma nova senha para garantir a segurança da sua conta.</p>");
		description.append("<p><a href=https://www.devpree.com.br/investme/login>");
		description.append("Acessar a LeCoffee</a></p>");
		description.append("<p>Caso você não tenha solicitado uma conta no InvestMe ");
		description.append("ou acredite que este email tenha sido enviado por engano, por favor, desconsidere esta mensagem.</p>");
		description.append("Atenciosamente, <br>");
		description.append("A equipe InvestMe <br>");
		
		TOLog log = new TOLog();
		log.setCategory(EnumLogCategory.WELCOME_EMAIL);
		log.setIp(this.getUserIpAddress());
		
		try {
			EmailUtil.sendMail(this.getClient().getEmail(), "Bem-vindo(a) ao InvestMe!", description.toString(), MessageUtil.getMessageFromProperties("msg_email_successfully_sent"));
			log.setStack("Email sent successfully. Credentials: " + this.getClient().getEmail());
			log.setType(EnumLogType.INFO);
		} catch (Exception e) {
			log.setStack("Credentials: " + this.getClient().getEmail() + ExceptionUtils.getStackTrace(e));
			log.setType(EnumLogType.EXCEPTION);
		}
		
		saveLog(log);
	}
	
	public void accessUserAccount() {
		this.getSession().setAttribute("client", this.getClient());
		
		RedirectURL.redirectTo("/investme/client/home");
	}
	
	// Getters and Settersclient
	public TOClient getClient() {
		return client;
	}

	public void setClient(TOClient client) {
		this.client = client;
	}

	public IKeepClientSBean getClientSBean() {
		return clientSBean;
	}
	
	public void setClientSBean(IKeepClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}
	
}
