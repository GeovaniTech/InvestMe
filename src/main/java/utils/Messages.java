package utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Messages {
	public void createMessage(FacesMessage.Severity severity, String titulo, String descricao) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, titulo, descricao));
	}
	
	public void emptyValues() {
		createMessage(FacesMessage.SEVERITY_ERROR, "Empty Values", "There are empty values");
	}
	
	public void saveSuccessfully() {
		createMessage(FacesMessage.SEVERITY_INFO, "Saved Successfully", "Successful registration");
	}
	
	public void removeSuccessfully() {
		createMessage(FacesMessage.SEVERITY_INFO, "Removed Successfully", "Record removed successfully");
	}
	
	public void errorRemoving() {
		createMessage(FacesMessage.SEVERITY_ERROR, "Error Removing", "Could not remove record");
	}
}
