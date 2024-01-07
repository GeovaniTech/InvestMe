package abstracts;

import java.util.List;
import java.util.Map;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import abstracts.AbstractMBean;
import abstracts.AbstractTOFilter;
import abstracts.AbstractTOObject;

public abstract class AbstractFilterMBean<DataModel extends AbstractTOObject, Filter extends AbstractTOFilter> extends AbstractMBean {

	private static final long serialVersionUID = 4515288235703540053L;
	
	private LazyDataModel<DataModel> results;
	private Filter filter;
	private List<DataModel> pageResults;
	
	public void searchResults() {
		this.setResults(new LazyDataModel<DataModel>() {

			private static final long serialVersionUID = -388135453341051527L;

			@Override
			public int count(Map<String, FilterMeta> filterBy) {
				return getCount(getFilter());
			}
			
			@Override
			public DataModel getRowData(String rowKey) {
				for(DataModel model : pageResults) {
					if(String.valueOf(model.getId()).equals(rowKey)) {
						return model;
					}
				}
				
				return null;
			}
			
			@Override
			public String getRowKey(DataModel object) {
				return String.valueOf(object.getId());
			}

			@Override
			public List<DataModel> load(int first, int pageSize, Map<String, SortMeta> sortBy,
					Map<String, FilterMeta> filterBy) {
				
				getFilter().setFirstResult(first);
				getFilter().setMaxResults(pageSize);
				
				setRowCount(getCount(getFilter()));
				
				pageResults = getData(getFilter());
				
				return pageResults;
			}
		
		});
	}
	
	public abstract void init();
	
	public abstract List<DataModel> getData(Filter filter);
	
	public abstract Integer getCount(Filter filter);

	public abstract void clearFilters();
	
	public abstract void onRowSelect(SelectEvent<DataModel> event);
	
	// Getters and Setters
	public LazyDataModel<DataModel> getResults() {
		return results;
	}
	
	public void setResults(LazyDataModel<DataModel> results) {
		this.results = results;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}
	
}
