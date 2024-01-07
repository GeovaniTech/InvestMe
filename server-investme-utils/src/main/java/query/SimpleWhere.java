package query;

import java.util.List;

import javax.sound.midi.Sequence;

import jakarta.persistence.TemporalType;
import to.TODateRangeFilter;
import to.TOInputFilter;
import to.TOInputNumberFilter;
import to.TOParameter;
import utils.SequenceGeneratorUtil;
import utils.StringUtil;

public class SimpleWhere {
	public static String queryFilter(String field, TOInputFilter filter) {
		if(StringUtil.isNotNull(filter.getValue())) {
			if(filter.getType().equals("contains")) {
				return " AND LOWER(" + field + ") LIKE '%" + filter.getValue().toLowerCase() + "%'";
			} else {
				return " AND LOWER(" + field + ") NOT LIKE '%" + filter.getValue().toLowerCase() + "%'";
			}
		} else {
			return "";
		}
	}
	
	public static String queryFilterNumberRange(String field, TOInputNumberFilter filter) {
		StringBuilder sql = new StringBuilder();
		
		if(filter.getFrom() != null) {
			sql.append(" AND " + field + " >= " + filter.getFrom());
		} 
		
		if (filter.getTo() != null){
			sql.append(" AND " + field + " <= " + filter.getTo());
		}
		
		return sql.toString();
	}
	
	public static String queryFilterDateRange(String field, TODateRangeFilter filter, TemporalType type, List<TOParameter> params) {
		StringBuilder sql = new StringBuilder();
		
		if(filter.getFrom() != null) {
			String paramName = SequenceGeneratorUtil.randomAlphabeticSequence(10);
			
			sql.append(" AND ").append(field);
			sql.append(" >= :").append(paramName);
			
			TOParameter param = new TOParameter(paramName, filter.getFrom(), type);
			params.add(param);
		} 
		
		if (filter.getTo() != null){
			String paramName = SequenceGeneratorUtil.randomAlphabeticSequence(10);
			
			sql.append(" AND ").append(field);
			sql.append(" <= :").append(paramName);
			
			TOParameter param = new TOParameter(paramName, filter.getTo(), type);
			params.add(param);
		}
		
		return sql.toString();
	}
}
