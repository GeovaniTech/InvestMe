package managedBean.client;

import java.util.List;
import java.util.Map;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import abstracts.AbstractMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.client.IKeepClientSBean;
import to.client.TOClient;
import to.client.TOFilterClient;

@Named("MBClient")
@ViewScoped
public class MBClient extends AbstractMBean {

	private static final long serialVersionUID = 9099066833372360110L;
	
	private LazyDataModel<TOClient> results;
	private TOFilterClient filter;
	private TOClient client;
	
	@EJB
	private IKeepClientSBean clientSBean;
	
	@PostConstruct
	public void init() {
		this.setFilter(new TOFilterClient());
		this.setClient(new TOClient());
		this.searchClients();
	}
	
	public void initNewClient() {
		this.getClientInfoMBean().setClient(new TOClient());
		this.getClientInfoMBean().setEditing(false);
	}
	
	public void clearFilters() {
		this.setFilter(new TOFilterClient());
	}
	
	public void searchClients() {
		this.setResults(new LazyDataModel<TOClient>() {
			
			private static final long serialVersionUID = 2153336984091642643L;
			
			private List<TOClient> clients;

			@Override
			public List<TOClient> load(int first, int pageSize, Map<String, SortMeta> sortBy,
					Map<String, FilterMeta> filterBy) {
				
				getFilter().setFirstResult(first);
				getFilter().setMaxResults(pageSize);
				
				setRowCount(getClientSBean().countClient(getFilter()));
				
				clients = getClientSBean().list(getFilter());
				
				return clients;
			}
			
			@Override
			public TOClient getRowData(String rowKey) {
				for(TOClient client : clients) {
					if(String.valueOf(client.getId()).equals(rowKey)) {
						return client;
					}
				}
				
				return null;
			}

			@Override
			public String getRowKey(TOClient object) {
				return String.valueOf(object.getId());
			}
			
			@Override
			public int count(Map<String, FilterMeta> filterBy) {
				return getClientSBean().countClient(getFilter());
			}
		});
	}

    public void onRowSelect(SelectEvent<TOClient> event) {
    	this.setClient(this.getClientSBean().findById(event.getObject().getId()));
    	
    	this.getClientInfoMBean().setClient(this.getClient());
    	this.getClientInfoMBean().setEditing(true);
    	
    	PrimeFaces.current().ajax().update("dialogClientInfo:tabview_client:infosClient");
    	PrimeFaces.current().ajax().update("dialogClientInfo:tabview_client:adressesClient");
    }
    
    public MBClientInfo getClientInfoMBean() {
    	return (MBClientInfo) this.getMBean("MBClientInfo");
    }
    
	// Getters and Setters
	public IKeepClientSBean getClientSBean() {
		return clientSBean;
	}

	public void setClientSBean(IKeepClientSBean clientSBean) {
		this.clientSBean = clientSBean;
	}

	public LazyDataModel<TOClient> getResults() {
		return results;
	}

	public void setResults(LazyDataModel<TOClient> results) {
		this.results = results;
	}

	public TOFilterClient getFilter() {
		return filter;
	}

	public void setFilter(TOFilterClient filter) {
		this.filter = filter;
	}

	public TOClient getClient() {
		return client;
	}

	public void setClient(TOClient client) {
		this.client = client;
	}
}
