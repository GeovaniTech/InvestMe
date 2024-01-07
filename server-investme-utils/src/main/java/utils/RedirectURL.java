package utils;

import java.io.IOException;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

public class RedirectURL {
	public static void redirectTo(String url) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

		try {
			ec.redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
