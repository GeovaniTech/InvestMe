package managedBean.appconfigs;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.primefaces.PrimeFaces;

import abstracts.AbstractMBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import keep.client.IKeepClientSBean;
import model.AppConfigs;
import to.client.TOClient;
import utils.CookieUtil;
import utils.ImageUtil;
import utils.RedirectURL;

@Named("MBAppConfigs")
@SessionScoped
public class MBAppConfigs extends AbstractMBean {

	private static final long serialVersionUID = 8432905268667991640L;
	
	private AppConfigs appConfigs;
	private List<Locale> localeList;
	
	@EJB
	private IKeepClientSBean clientSBean;

	public MBAppConfigs() {
		//Attributes
		this.setAppConfigs(new AppConfigs());
		this.setLocaleList(new ArrayList<Locale>());
		this.getLocaleList().add(new Locale("pt_BR"));
		this.getLocaleList().add(new Locale("en_US"));
		
		//Initial Configurations
		this.getAppConfigs().setLanguage(Locale.getDefault().getLanguage());
		this.getAppConfigs().setDarkMode(false);
		this.getAppConfigs().setShowValues(false);
		
		//Getting User preferences
		this.getConfigsFromCookies();	
	}
	
 	public boolean getConfigsFromCookies() {
 		this.getAppConfigs().setDarkMode(CookieUtil.getDarkModeCookie());
 		this.getAppConfigs().setShowValuesStartUp(CookieUtil.getShowCardValuesOnStartUp());
 		
 		if(this.getAppConfigs().isShowValuesStartUp()) {
 	 		this.getAppConfigs().setShowValues(true);
 		}
 		
 		if(CookieUtil.getLanguageCookie() != null) {
 			this.getAppConfigs().setLanguage(CookieUtil.getLanguageCookie());
 			
 			return true;
 		}
 		return false;
 	}
	
	public void createCookiePreferences() {	
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		Cookie darkMode = new Cookie("darkMode", "" + this.getAppConfigs().isDarkMode());
		darkMode.setMaxAge(60*60*24*30);
		darkMode.setPath("/investme");
		
		Cookie language = new Cookie("language", this.getAppConfigs().getLanguage());
		language.setMaxAge(60*60*24*30);
		language.setPath("/investme");
		
		Cookie showValuesOnStartUp = new Cookie("showValuesOnStartUp", "" + this.getAppConfigs().isShowValuesStartUp());
		showValuesOnStartUp.setMaxAge(60*60*24*30);
		showValuesOnStartUp.setPath("/investme");
		
		response.addCookie(darkMode);
		response.addCookie(language);
		response.addCookie(showValuesOnStartUp);
	}
	
	public void removeUserFromCookie() {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		Cookie userSession = new Cookie("userSession", null);
		userSession.setMaxAge(60*60*24*30);
		userSession.setPath("/investme");
		
		response.addCookie(userSession);
	}
	
	
	public void updateCards() {
		PrimeFaces.current().executeScript("updateTableAndCards();");
	}
	
	public void logout() {
		removeUserFromCookie();
		finishSession();
		
		RedirectURL.redirectTo("/investme/login");
	}
	
	public boolean isUserLogged() {
		if(this.getClientSession() != null) {
			return true;
		}
		
		return false;
	}
	
	public boolean isUserAdminLogged() {
		if(this.getClientSession() != null && this.getClientLogged().getSecurityLevel().equals("admin")) {
			return true;
		}
		
		return false;
	}
	
	public void redirectTo(String url) {
		RedirectURL.redirectTo(url);
	}
	
	public String getBrazilianCurrency(Double value) {
		Locale localeBR = new Locale("pt", "BR");
		NumberFormat brazilianFormat = NumberFormat.getCurrencyInstance(localeBR);
		String formattedValue = brazilianFormat.format(value);

		return formattedValue;
	}

	public String getRenderedImage(byte[] imageBytes) {
		return ImageUtil.geRenderedImage(imageBytes);
	}

	public void refreshPage() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}
	
	public String getSystemVersion() {
		 FacesContext facesContext = FacesContext.getCurrentInstance(); 
		 Locale locale = facesContext.getViewRoot().getLocale();
		 ResourceBundle bundle = ResourceBundle.getBundle("app-config", locale);

		 return bundle.getString("system_version");

	}
	
	// Getters and Setters
	public AppConfigs getAppConfigs() {
		return appConfigs;
	}
	public void setAppConfigs(AppConfigs appConfigs) {
		this.appConfigs = appConfigs;
	}
	public List<Locale> getLocaleList() {
		return localeList;
	}
	public void setLocaleList(List<Locale> localeList) {
		this.localeList = localeList;
	}
	public TOClient getClientLogged() {
		return this.getClientSession();
	}
	public IKeepClientSBean getClientSBean() {
		return clientSBean;
	}
	public void setClientSBean(IKeepClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}
	
}

