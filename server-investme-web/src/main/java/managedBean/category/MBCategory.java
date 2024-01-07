package managedBean.category;

import java.util.List;

import org.primefaces.event.SelectEvent;

import abstracts.AbstractFilterMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.category.IKeepCategorySBean;
import to.category.TOCategory;
import to.category.TOFilterCategory;

@Named(MBCategory.MANAGED_BEAN_NAME)
@ViewScoped
public class MBCategory extends AbstractFilterMBean<TOCategory, TOFilterCategory> {

	private static final long serialVersionUID = 7093006273130417671L;
	public static final String MANAGED_BEAN_NAME = "MBCategory";
	
	private TOCategory category;
	
	@EJB
	private IKeepCategorySBean categorySBean;
	
	public MBCategory() {
		this.searchResults();
	}
	
	@PostConstruct
	@Override
	public void init() {
		this.clearFilters();
		this.searchResults();
	}

	public void initCategory() {
		this.getMBCategoryInfo().initCategory();
	}
	
	@Override
	public List<TOCategory> getData(TOFilterCategory filter) {
		return this.getCategorySBean().searchCategories(filter);
	}

	@Override
	public Integer getCount(TOFilterCategory filter) {
		return this.getCategorySBean().getCount(filter);
	}

	@Override
	public void clearFilters() {
		this.setFilter(new TOFilterCategory());	
	}

	@Override
	public void onRowSelect(SelectEvent<TOCategory> event) {
		this.getMBCategoryInfo().editCategory(event.getObject());
	}
	
	public MBCategoryInfo getMBCategoryInfo() {
		return this.getMBean(MBCategoryInfo.MANAGED_BEAN_NAME);
	}

	public TOCategory getCategory() {
		return category;
	}

	public void setCategory(TOCategory category) {
		this.category = category;
	}

	public IKeepCategorySBean getCategorySBean() {
		return categorySBean;
	}

	public void setCategorySBean(IKeepCategorySBean categorySBean) {
		this.categorySBean = categorySBean;
	}
	
}
