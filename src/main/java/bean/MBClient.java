package bean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import manter.client.ManterClient;
import to.TOClient;
import utils.AbstractBean;
import utils.RedirectUrl;

@Named("MBclient")
@ViewScoped
public class MBClient extends AbstractBean {
	private static final long serialVersionUID = -8527177710843576826L;
	
	private TOClient client;
	private String password;
	
	
	private ManterClient manterClient;
	
	public MBClient() {
		this.client = new TOClient();
		this.manterClient = new ManterClient();
	}
	
	public void validateAccess() {		
		if(this.getManterClient().validateAccess(client, password)) {
			getSession().setAttribute("client", client);
			
			RedirectUrl.redirecionarPara("/investme/pages/investments.xhtml");
		} else {
			msg.createMessage(FacesMessage.SEVERITY_ERROR, "User not found", "User not found, try again.");
		}
	}

	public void logout() {
		getSession().setAttribute("client", null);
		
		RedirectUrl.redirecionarPara("/investme/pages/investments.xhtml");
	}
	
	public TOClient getClient() {
		return client;
	}

	public void setClient(TOClient client) {
		this.client = client;
	}

	public ManterClient getManterClient() {
		return manterClient;
	}

	public void setManterClient(ManterClient manterClient) {
		this.manterClient = manterClient;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
