package keep.category;

import java.util.List;

import jakarta.ejb.Local;
import to.category.TOCategory;
import to.category.TOFilterCategory;

@Local
public interface IKeepCategorySBean {
	public void save(TOCategory category);
	public void change(TOCategory category);
	public void remove(TOCategory category);
	public TOCategory findById(int id);
	public Integer getCount(TOFilterCategory filter);
	public List<TOCategory> searchCategories(TOFilterCategory filter);
	public List<TOCategory> searchCategoriesByType(String type);
}
