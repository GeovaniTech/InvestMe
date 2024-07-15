package br.com.devpree.investme.api.category.service;

import java.util.List;

import br.com.devpree.investme.api.category.transferobject.TOCategoryRestModel;
import br.com.devpree.investme.api.common.AbstractService;
import br.com.devpree.investme.api.common.model.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class CategoryService extends AbstractService<Category, TOCategoryRestModel> {
	
	private static final long serialVersionUID = 258277886950868095L;

	public CategoryService() {
		super(Category.class, TOCategoryRestModel.class);
	}

	@SuppressWarnings("unchecked")
	public List<TOCategoryRestModel> list(String email) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT C FROM ")
			.append(Category.class.getSimpleName())
			.append(" C ")
			.append(" WHERE C.creationUser = :email ");
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Category.class);
		query.setParameter("email", email);
		
		return this.convertModelResults(query.getResultList());
	}
}
