package brapi.webservice;

import java.util.List;

import brapi.transferobject.TOStockBrapiAPI;
import brapi.transferobject.TOTickerBrapiAPI;
import jakarta.ejb.Local;

@Local
public interface IBrapiWebServiceSBean {
	public TOTickerBrapiAPI getTicker(String symbol);
	public List<TOStockBrapiAPI> getAllStocks();
	
}
