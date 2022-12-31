package utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import to.TOClient;

public class InvestmeSession {
	protected HttpSession getSession() {
		return(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
	protected TOClient getClient() {
		return (TOClient) getSession().getAttribute("client");
	}
}
