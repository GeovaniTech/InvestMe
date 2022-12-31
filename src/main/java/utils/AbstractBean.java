package utils;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import interfaces.Messages;

public class AbstractBean implements Serializable, Messages {
	private static final long serialVersionUID = -5888518947738583514L;

	protected HttpSession getSession() {
		return(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
}
