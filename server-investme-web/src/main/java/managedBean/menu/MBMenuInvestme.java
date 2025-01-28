package managedBean.menu;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import abstracts.AbstractMBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named(MBMenuInvestme.MANAGED_BEAN_NAME)
@ViewScoped
public class MBMenuInvestme extends AbstractMBean {

	private static final long serialVersionUID = 4863287155259223199L;
	public static final String MANAGED_BEAN_NAME = "MBMenuInvestme";

	private MenuModel menu;

	@PostConstruct
	public void init() {
		this.setMenu(new DefaultMenuModel());
		
        DefaultSubMenu financialManagement = DefaultSubMenu.builder()
                .label(this.getLabel("financial_management"))
                .styleClass("submenu")
                .expanded(true)
                .build();
        
        DefaultMenuItem wallet = DefaultMenuItem.builder()
        		.styleClass("menu-wallet")
                .value(this.getLabel("wallet"))
                .icon("pi pi-wallet")
                .url(this.createUrlMenu("client/wallet"))
                .update("messages")
                .build();
        
        DefaultMenuItem categories = DefaultMenuItem.builder()
        		.styleClass("menu-categories")
                .value(this.getLabel("categories"))
                .icon("pi pi-box")
                .url(this.createUrlMenu("client/categories"))
                .update("messages")
                .build();
        
        DefaultMenuItem payments = DefaultMenuItem.builder()
        		.styleClass("menu-payments")
                .value(this.getLabel("payments"))
                .icon("pi pi-credit-card")
                .url(this.createUrlMenu("client/payments"))
                .update("messages")
                .build();
        
        DefaultMenuItem stockExchange = DefaultMenuItem.builder()
        		.styleClass("menu-dashboard")
                .value(this.getLabel("stock_exchange"))
                .icon("pi pi-dollar")
                .url(this.createUrlMenu("client/stockexchange"))
                .update("messages")
                .build();
        
        financialManagement.getElements().add(wallet);
        financialManagement.getElements().add(stockExchange);
        financialManagement.getElements().add(categories);
        financialManagement.getElements().add(payments);
        
        this.getMenu().getElements().add(financialManagement);
        
        if(this.getClientSession().getSecurityLevel().equals("admin")) { 
            DefaultSubMenu admin = DefaultSubMenu.builder()
                    .label("Admin")
                    .styleClass("submenu")
                    .expanded(true)
                    .build();
            
            DefaultMenuItem users = DefaultMenuItem.builder()
            		.styleClass("menu-users")
                    .value(this.getLabel("users"))
                    .icon("pi pi-user")
                    .url(this.createUrlMenu("admin/users"))
                    .build();
            
            DefaultMenuItem logs = DefaultMenuItem.builder()
            		.styleClass("menu-logs")
                    .value(this.getLabel("logs"))
                    .icon("pi pi-exclamation-circle")
                    .url(this.createUrlMenu("admin/logs"))
                    .build();
            
            admin.getElements().add(users);
            admin.getElements().add(logs);
            
            this.getMenu().getElements().add(admin);
        }
	}
	
	private String createUrlMenu(String pageUrl) {
		return this.getRequest().getContextPath() + "/" + pageUrl;
	}
	
	// Getters and Setters
	public MenuModel getMenu() {
		return menu;
	}

	public void setMenu(MenuModel menu) {
		this.menu = menu;
	}
		
}
