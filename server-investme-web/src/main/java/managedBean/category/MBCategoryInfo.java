package managedBean.category;

import java.util.Date;

import org.primefaces.PrimeFaces;

import abstracts.AbstractMBean;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.category.IKeepCategorySBean;
import to.category.TOCategory;

@Named(MBCategoryInfo.MANAGED_BEAN_NAME)
@ViewScoped
public class MBCategoryInfo extends AbstractMBean {

	private static final long serialVersionUID = -209387974542831500L;
	public static final String MANAGED_BEAN_NAME = "MBCategoryInfo";
	
	private TOCategory category;
	private boolean editing;
	
	@EJB
	private IKeepCategorySBean categorySBean;
	
	public MBCategoryInfo() {
		this.initCategory();
	}
	
	public void save() {
		try {
			this.getCategory().setCreationDate(new Date());
			this.getCategory().setCreationUser(this.getClientSession().getEmail());
			
			this.getCategorySBean().save(this.getCategory());
			this.setEditing(true);
			showMessageItemSaved(this.getCategory().getName());
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public void change() {
		try {
			this.getCategory().setChangeDate(new Date());
			this.getCategory().setChangeUser(this.getClientSession().getEmail());

			this.getCategorySBean().change(this.getCategory());
			showMessageItemChanged(this.getCategory().getName());
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public void remove() {
		try {
			this.getCategorySBean().remove(this.getCategory());
			showMessageItemRemoved(this.getCategory().getName());
			
			this.initCategory();
		} catch (Exception e) {
			showMessageError(e);
		}
	}
	
	public void initCategory() {
		this.setEditing(false);
		this.setCategory(new TOCategory());
		
		this.updateFormCategory();
	}
	
	public void editCategory(TOCategory category) {
		this.setEditing(true);
		this.setCategory(category);
		
		this.updateFormCategory();
	}
	
	public void updateFormCategory() {
		PrimeFaces.current().ajax().update("dialogCategoryInfo:formCategoryInfo");
	}

	public TOCategory getCategory() {
		return category;
	}

	public void setCategory(TOCategory category) {
		this.category = category;
	}

	public boolean isEditing() {
		return editing;
	}

	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public IKeepCategorySBean getCategorySBean() {
		return categorySBean;
	}

	public void setCategorySBean(IKeepCategorySBean categorySBean) {
		this.categorySBean = categorySBean;
	}

}
