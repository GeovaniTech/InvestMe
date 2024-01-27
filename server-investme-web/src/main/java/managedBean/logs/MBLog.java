package managedBean.logs;

import java.util.Arrays;
import java.util.List;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import abstracts.AbstractFilterMBean;
import enums.EnumLogCategory;
import enums.EnumLogType;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.logs.IKeepLogSbean;
import to.logs.TOFilterLog;
import to.logs.TOLog;
import utils.DateUtil;

@Named(MBLog.MANAGED_BEAN_NAME)
@ViewScoped
public class MBLog extends AbstractFilterMBean<TOLog, TOFilterLog> {

	private static final long serialVersionUID = 3119946574960832437L;
	public static final String MANAGED_BEAN_NAME = "MBLog";
	
	private List<EnumLogCategory> categories;
	private List<EnumLogType> types;
	
	private TOLog log;
	
	@EJB
	private IKeepLogSbean logSbean;
	
	@PostConstruct
	@Override
	public void init() {
		this.clearFilters();
		this.searchResults();
	}

	public void save(TOLog log) {
		this.getLogSbean().save(log);
	}

	@Override
	public List<TOLog> getData(TOFilterLog filter) {
		return this.getLogSbean().list(filter);
	}

	@Override
	public Integer getCount(TOFilterLog filter) {
		return this.getLogSbean().count(filter);
	}

	@Override
	public void clearFilters() {
		this.setFilter(new TOFilterLog());
		this.setCategories(Arrays.asList(EnumLogCategory.values()));
		this.setTypes(Arrays.asList(EnumLogType.values()));
		this.getFilter().getDate().setFrom(DateUtil.removeTime(this.getFilter().getDate().getFrom()));
	}

	@Override
	public void onRowSelect(SelectEvent<TOLog> event) {
		this.setLog(event.getObject());
		PrimeFaces.current().ajax().update("dialogStackDetails:formStackDetails");
	}

	// Getters and Setters
	public IKeepLogSbean getLogSbean() {
		return logSbean;
	}

	public void setLogSbean(IKeepLogSbean logSbean) {
		this.logSbean = logSbean;
	}

	public List<EnumLogCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<EnumLogCategory> categories) {
		this.categories = categories;
	}

	public List<EnumLogType> getTypes() {
		return types;
	}

	public void setTypes(List<EnumLogType> types) {
		this.types = types;
	}

	public TOLog getLog() {
		return log;
	}

	public void setLog(TOLog log) {
		this.log = log;
	}

}
