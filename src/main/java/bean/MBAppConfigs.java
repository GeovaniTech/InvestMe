package bean;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import utils.AbstractBean;
import utils.RedirectUrl;

@Named("MBAppConfigs")
@SessionScoped
public class MBAppConfigs extends AbstractBean {
	private static final long serialVersionUID = 1674607426582510645L;
	
	private boolean darkMode = false;
	private List<Locale> localeList;
	private String language;
	
	public MBAppConfigs() {
		this.localeList = new ArrayList<Locale>();
		localeList.add(new Locale("pt"));
		localeList.add(new Locale("en"));
		
		language = "pt";
	}
	
	public String getBrazilianCurrency(Double value) {
		Locale localeBR = new Locale("pt", "BR");
		NumberFormat brazilianFormat = NumberFormat.getCurrencyInstance(localeBR);
		
		return brazilianFormat.format(value);
	}
	
	public void redirectTo(String url) {
		RedirectUrl.redirectTo(url);
	}
	
	public void logout() {
		this.getSession().invalidate();
		
		this.redirectTo("/investme");
	}

	public boolean isDarkMode() {
		return darkMode;
	}

	public void setDarkMode(boolean darkMode) {
		this.darkMode = darkMode;
	}

	public List<Locale> getLocaleList() {
		return localeList;
	}

	public void setLocaleList(List<Locale> localeList) {
		this.localeList = localeList;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
