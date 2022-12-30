package bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import manter.ManterClient;
import to.TOClient;

@Named("MBclient")
@ViewScoped
public class MBClient implements Serializable {
	private static final long serialVersionUID = -8527177710843576826L;
	
	private TOClient client;
	private ManterClient manterClient;
	
	public MBClient() {
		this.client = new TOClient();
		this.manterClient = new ManterClient();
	}
	
	public void validateAccess() {
		this.getManterClient().validateAccess(client);
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
}
