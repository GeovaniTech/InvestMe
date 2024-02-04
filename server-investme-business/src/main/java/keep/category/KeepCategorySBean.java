package keep.category;

import java.util.ArrayList;
import java.util.List;

import abstracts.AbstractKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import model.Category;
import query.SimpleWhere;
import to.TOParameter;
import to.category.TOCategory;
import to.category.TOFilterCategory;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepCategorySBean extends AbstractKeep<Category, TOCategory> implements IKeepCategorySBean, IKeepCategoryRemoteSbean {
	public KeepCategorySBean() {
		super(Category.class, TOCategory.class);
	}

	@Override
	public void save(TOCategory category) {
		Category model = this.convertToModel(category);
		
		this.getEntityManager().persist(model);
		category.setId(model.getId());
	}

	@Override
	public void change(TOCategory category) {
		Category model = this.convertToModel(category);
		
		this.getEntityManager().merge(model);
	}

	@Override
	public void remove(TOCategory category) {
		Category model = this.convertToModel(category);

		this.getEntityManager().remove(this.getEntityManager().contains(model) ? model : this.getEntityManager().merge(model));
	}

	@Override
	public TOCategory findById(int id) {
		return this.convertToDTO(this.getEntityManager().find(Category.class, id));
	}

	@Override
	public Integer getCount(TOFilterCategory filter) {
		StringBuilder sql = new StringBuilder();
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append("SELECT COUNT(C.id) FROM ")
			.append(getFromCategories())
			.append(getWhereCategories(filter, params));
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		setParameters(query, params);
		
		Number value = (Number) query.getSingleResult();
		
		return value.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TOCategory> searchCategories(TOFilterCategory filter) {
		StringBuilder sql = new StringBuilder();
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append("SELECT C FROM ")
			.append(getFromCategories())
			.append(getWhereCategories(filter, params));
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setFirstResult(filter.getFirstResult());
		query.setMaxResults(filter.getMaxResults());
		setParameters(query, params);
		
		return this.convertModelResults(query.getResultList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TOCategory> searchCategoriesByType(String type) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT C FROM ")
			.append(getFromCategories())
			.append(" WHERE C.type = :type")
			.append(" AND C.creationUser = :email ");
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("type", type);
		query.setParameter("email", this.getClientSession().getEmail());
		
		return this.convertModelResults(query.getResultList());
	}
	
	public String getFromCategories() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(Category.class.getSimpleName()).append(" C ");
		
		return sql.toString();
	}
	
	public String getWhereCategories(TOFilterCategory filter, List<TOParameter> params) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" WHERE C.creationUser = :email ");
		
		params.add(new TOParameter("email", this.getClientSession().getEmail()));
		
		sql.append(SimpleWhere.queryFilter("C.name", filter.getName()));
		sql.append(SimpleWhere.queryFilter("C.description", filter.getDescription()));
		
		return sql.toString();
	}
	
}
