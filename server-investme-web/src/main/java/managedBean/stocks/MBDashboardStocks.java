package managedBean.stocks;

import java.util.List;
import java.util.stream.Collectors;

import org.primefaces.PrimeFaces;

import abstracts.AbstractMBean;
import brapi.transferobject.TOStockBrapiAPI;
import brapi.transferobject.TOTickerBrapiAPI;
import brapi.webservice.IBrapiWebServiceSBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import managedBean.transaction.MBTransactionInfo;
import utils.MoneyUtil;
import utils.StringUtil;

@Named(MBDashboardStocks.MANAGED_BEAN_NAME)
@SessionScoped
public class MBDashboardStocks extends AbstractMBean {
	
	public static final String MANAGED_BEAN_NAME = "MBDashbordStocks";
	private static final long serialVersionUID = 6250615529665540053L;
	
	private List<TOStockBrapiAPI> stocks;
	private List<TOStockBrapiAPI> filteredStocks;
	
	private String filter;
	
	@EJB
	private IBrapiWebServiceSBean brapiWebService;

	@PostConstruct
	public void init() {
		this.setStocks(this.getBrapiWebService().getAllStocks());
		this.setFilteredStocks(this.getStocks());
	}
	
	public void clearFilters() {
		this.setFilter(null);
	}
	
	public TOTickerBrapiAPI findStock(String symbol) {
		return this.getBrapiWebService().getTicker(symbol);
	}
	
	public void buyStock(String symbol, Double price) {
		this.startInvestment();
		
		TOTickerBrapiAPI stock = this.findStock(symbol);

		if (StringUtil.isNotNull(stock.getSymbol())) {
			this.getMBTransactionInfo().getTransaction().setActive(stock.getSymbol());
			this.getMBTransactionInfo().getTransaction().setPrice(stock.getCurrentPrice());
		} else {
			this.getMBTransactionInfo().getTransaction().setActive(symbol);
			this.getMBTransactionInfo().getTransaction().setPrice(price);
		}

		PrimeFaces.current().ajax().update("dialogTransactionInfo:formTransactionInfo:name");
		PrimeFaces.current().ajax().update("dialogTransactionInfo:formTransactionInfo:price");
	}
	
	private void startInvestment() {
		if (this.getMBTransactionInfo().getTransaction() == null) {
			this.getMBTransactionInfo().initTransaction();
			this.getMBTransactionInfo().initInvestment();
		}
	}
	
	public MBTransactionInfo getMBTransactionInfo() {
		return this.getMBean(MBTransactionInfo.MANAGED_BEAN_NAME);
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

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public List<TOStockBrapiAPI> getFilteredStocks() {
		List<TOStockBrapiAPI> stocks = this.getStocks().stream().collect(Collectors.toList());
		
		if (StringUtil.isNotNull(this.getFilter())) {
			filteredStocks = stocks.stream()
	                .filter(item -> item.getName().toLowerCase().contains(this.getFilter().toLowerCase()) 
                			|| item.getActive().toLowerCase().contains(this.getFilter().toLowerCase())
                			|| MoneyUtil.getBrazilianCurrency(item.getPrice()).contains(this.getFilter()))
	                .collect(Collectors.toList());
		} else {
			filteredStocks = stocks;
		}
		
		return filteredStocks;
	}

	public void setFilteredStocks(List<TOStockBrapiAPI> filteredStocks) {
		this.filteredStocks = filteredStocks;
	}
	
}
