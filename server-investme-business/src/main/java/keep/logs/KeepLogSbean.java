package keep.logs;

import java.util.ArrayList;
import java.util.List;

import abstracts.AbstractKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;
import model.Log;
import query.SimpleWhere;
import to.TOParameter;
import to.logs.TOFilterLog;
import to.logs.TOLog;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepLogSbean extends AbstractKeep<Log, TOLog> implements IKeepLogSbean, IKeepLogRemoteSBean {
	public KeepLogSbean() {
		super(Log.class, TOLog.class);
	}

	@Override
	public void save(TOLog log) {
		Log model = this.convertToModel(log);
		
		this.getEntityManager().persist(model);
	}

	@Override
	public Integer count(TOFilterLog filter) {
		StringBuilder sql = new StringBuilder();
	
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT COUNT(L.id) ")
			.append(this.getFromLogs())
			.append(this.getWhereLogs(filter, params));		
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		setParameters(query, params);
		
		Number result = (Number) query.getSingleResult();
		
		return result.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TOLog> list(TOFilterLog filter) {
		StringBuilder sql = new StringBuilder();
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(" SELECT L ")
			.append(this.getFromLogs())
			.append(this.getWhereLogs(filter, params))
			.append(" ORDER BY L.creationDate DESC ");		
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setFirstResult(filter.getFirstResult());
		query.setMaxResults(filter.getMaxResults());
		setParameters(query, params);
		
		return this.convertModelResults(query.getResultList());
	}
	
	private String getFromLogs() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" FROM ")
			.append(Log.class.getSimpleName())
			.append(" L ");
		
		return sql.toString();
	}
	
	private String getWhereLogs(TOFilterLog filter, List<TOParameter> params) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" WHERE 1 = 1 ");
		
		sql.append(SimpleWhere.queryFilter("L.creationUser", filter.getEmail()));
		sql.append(SimpleWhere.queryFilter("L.message", filter.getMessage()));
		sql.append(SimpleWhere.queryFilter("L.ip", filter.getIp()));
		sql.append(SimpleWhere.queryFilterDateRange("L.creationDate", filter.getDate(), TemporalType.TIMESTAMP, params));
		
		if(filter.getCategory() != null) {
			sql.append(" AND L.category = :category ");
			params.add(new TOParameter("category", filter.getCategory()));
		}
		
		if(filter.getType() != null) {
			sql.append(" AND L.type = :type ");
			params.add(new TOParameter("type", filter.getType()));
		}
		
		return sql.toString();
	}
	
}
