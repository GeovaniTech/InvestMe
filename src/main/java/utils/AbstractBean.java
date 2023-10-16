package utils;

import java.io.Serializable;

import jakarta.faces.context.FacesContext;

import jakarta.servlet.http.HttpSession;
import to.TOClient;

public class AbstractBean implements Serializable {
	private static final long serialVersionUID = -5888518947738583514L;

	protected HttpSession getSession() {
		return(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
	protected TOClient getClient() {
		TOClient client = (TOClient) getSession().getAttribute("client");
		
		return client;
	}
}
