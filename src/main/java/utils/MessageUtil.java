package utils;

import java.util.Locale;
import java.util.ResourceBundle;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

public class MessageUtil {
	public static String getMessageFromProperties(String label) {
		 FacesContext facesContext = FacesContext.getCurrentInstance(); 
		 Locale locale = facesContext.getViewRoot().getLocale();
		 ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
		 
		 return bundle.getString(label);
	}
	
	public static void sendMessage(String message, FacesMessage.Severity severity) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, null));
	}
}
