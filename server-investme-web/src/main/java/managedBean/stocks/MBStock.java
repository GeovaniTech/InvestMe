package managedBean.stocks;

import java.util.List;

import abstracts.AbstractMBean;
import brapi.transferobject.TOStockBrapiAPI;
import brapi.webservice.IBrapiWebServiceSBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class MBStock extends AbstractMBean {

	private static final long serialVersionUID = 6250615529665540053L;
	
	private List<TOStockBrapiAPI> stocks;
	
	@EJB
	private IBrapiWebServiceSBean brapiWebService;

	@PostConstruct
	public void init() {
		this.setStocks(this.getBrapiWebService().getAllStocks());
	}
	
	public List<TOStockBrapiAPI> getStocks() {
		return stocks;
	}

	public void setStocks(List<TOStockBrapiAPI> stocks) {
		this.stocks = stocks;
	}

	public IBrapiWebServiceSBean getBrapiWebService() {
		return brapiWebService;
	}

	public void setBrapiWebService(IBrapiWebServiceSBean brapiWebService) {
		this.brapiWebService = brapiWebService;
	}
	
	
}
