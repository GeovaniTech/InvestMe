package utils;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import to.TOClient;

public class InvestmeSession {
	protected HttpSession getSession() {
		return(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
	protected TOClient getClient() {
		return (TOClient) getSession().getAttribute("client");
	}
}
