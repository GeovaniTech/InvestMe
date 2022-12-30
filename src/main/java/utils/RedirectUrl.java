package utils;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class RedirectUrl {
	public static void redirecionarPara(String url) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

		try {
			ec.redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
