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

	public void changedSuccessfully() {
		createMessage(FacesMessage.SEVERITY_INFO, "Change Successfully", "Changed registration");
	}
	
	public void removeSuccessfully() {
		createMessage(FacesMessage.SEVERITY_INFO, "Removed Successfully", "Record removed successfully");
	}
	
	public void errorRemoving() {
		createMessage(FacesMessage.SEVERITY_ERROR, "Error Removing", "Could not remove record");
	}
	
	public void erroNameUsed() {
		createMessage(FacesMessage.SEVERITY_ERROR, "Error Register", "The user name is being used!");
	}
	
	public void passwordsDontMatch() {
		createMessage(FacesMessage.SEVERITY_ERROR, "Error Register", "The passwords do not match");
	}
	
	public void errorGenerateReport() {
		createMessage(FacesMessage.SEVERITY_ERROR, "The report was not generated", "We couldn't generate the report, try again later");
	}
}
