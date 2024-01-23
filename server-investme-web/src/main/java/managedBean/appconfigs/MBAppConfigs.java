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
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import keep.client.IKeepClientSBean;
import to.appconfigs.TOAppConfig;
import to.client.TOClient;
import utils.CookieUtil;
import utils.ImageUtil;
import utils.MessageUtil;
import utils.RedirectURL;
import utils.StringUtil;

@Named("MBAppConfigs")
@SessionScoped
public class MBAppConfigs extends AbstractMBean {

	private static final long serialVersionUID = 8432905268667991640L;
	public static final String MANAGED_BEAN_NAME = "MBAppConfigs";
	
	private TOAppConfig appConfigs;
	private List<Locale> localeList;
	
	// For actions that require password
	private String password;
	
	@EJB
	private IKeepClientSBean clientSBean;

	public MBAppConfigs() {
		//Attributes
		this.setAppConfigs(new TOAppConfig());
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
	
	public void askShowValues() {
		if(!this.getAppConfigs().isShowValues() && !this.isValidPasswordForAction()) {
			PrimeFaces.current().executeScript("PF('dialogShowValuesVW').show();");
			this.getAppConfigs().setShowValues(false);
			return;
		} else if(!this.getAppConfigs().isShowValues()) {
			this.confirmShowValues();
			return;
		} else {
			this.getAppConfigs().setShowValues(false);;
		}
		
		this.updateCards();
		this.updateUserConfigs();
	}
	
	public void askShowValuesOnStartUp() {
		if(!this.getAppConfigs().isShowValuesStartUp() && !this.isValidPasswordForAction()) {
			PrimeFaces.current().executeScript("PF('dialogShowValuesOnStartUpVW').show();");
			this.getAppConfigs().setShowValuesStartUp(false);
			return;
		} else if(!this.getAppConfigs().isShowValuesStartUp()) {
			this.confirmShowValuesOnStartUp();
			return;
		} else {
			this.getAppConfigs().setShowValuesStartUp(false);
		}
		
		this.updateUserConfigs();
	}
	
	public void confirmShowValues() {
		if(this.isValidPasswordForAction()) {
			this.getAppConfigs().setShowValues(true);
			PrimeFaces.current().executeScript("PF('dialogShowValuesVW').hide();");
		} else {
			this.getAppConfigs().setShowValues(false);
		}
		
		this.updateCards();
		this.updateUserConfigs();
	}
	
	public void confirmShowValuesOnStartUp() {
		if(this.isValidPasswordForAction()) {
			this.getAppConfigs().setShowValuesStartUp(true);
			PrimeFaces.current().executeScript("PF('dialogShowValuesOnStartUpVW').hide();");
		} else {
			this.getAppConfigs().setShowValuesStartUp(false);
		}

		this.updateUserConfigs();
	}

	public boolean isValidPasswordForAction() {
		if(this.getPassword() != null && this.getClientSBean().isPasswordValidForAction(this.getClientLogged().getId(), this.getPassword())) {
			return true;
		} else if(StringUtil.isNotNull(this.getPassword())){
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("password_invalid"), FacesMessage.SEVERITY_ERROR);
		}
		
		return false;
	}
	
 	public boolean getConfigsFromCookies() {
 		this.getAppConfigs().setDarkMode(CookieUtil.getDarkModeCookie());
 		
 		if(CookieUtil.getLanguageCookie() != null) {
 			this.getAppConfigs().setLanguage(CookieUtil.getLanguageCookie());
 			
 			return true;
 		}
 		return false;
 	}
 	
 	public void configAppByUserPreferences() {
 		if(this.getClientLogged() != null && this.getClientLogged().getAppConfig() != null) {
 			this.setAppConfigs(this.getClientLogged().getAppConfig());
 			
 			if(!this.getAppConfigs().isShowValuesStartUp()) {
 				this.getAppConfigs().setShowValues(false);
 			} else {
 				this.getAppConfigs().setShowValues(true);
 			} 
 			
 			this.createCookiePreferences();
 		} else {
 			updateUserConfigs();
 		}
 	}
 	
 	public void updateUserConfigs() {
 		this.getClientLogged().setAppConfig(this.getAppConfigs());
 		this.getClientSBean().change(this.getClientLogged());
 		
 		this.createCookiePreferences();
 	}
	
	public void createCookiePreferences() {	
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		Cookie darkMode = new Cookie("darkMode", "" + this.getAppConfigs().isDarkMode());
		darkMode.setMaxAge(60*60*24*30);
		darkMode.setPath("/investme");
		
		Cookie language = new Cookie("language", this.getAppConfigs().getLanguage());
		language.setMaxAge(60*60*24*30);
		language.setPath("/investme");
		
		response.addCookie(darkMode);
		response.addCookie(language);
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
	public TOAppConfig getAppConfigs() {
		return appConfigs;
	}
	public void setAppConfigs(TOAppConfig appConfigs) {
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}

