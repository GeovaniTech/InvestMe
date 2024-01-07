package abstracts;

import java.io.Serializable;

import to.TODateRangeFilter;

public abstract class AbstractTOFilter implements Serializable {
	private static final long serialVersionUID = 9051615030433988009L;
	
	private int firstResult;
	private int maxResults;
	private boolean sort;
	
	// General Informations
	private TODateRangeFilter creationDate;
	private TODateRangeFilter changeDate;
	private TODateRangeFilter inactivationDate;
	private String creationUser;
	private String changeUser;
	private String inactivationUser;
	
	public AbstractTOFilter() {
		this.setCreationDate(new TODateRangeFilter());
		this.setChangeDate(new TODateRangeFilter());
		this.setInactivationDate(new TODateRangeFilter());
	}
	
	// Getters and Setters
	public int getFirstResult() {
		return firstResult;
	}
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}
	public int getMaxResults() {
		return maxResults;
	}
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
	public boolean isSort() {
		return sort;
	}
	public void setSort(boolean sort) {
		this.sort = sort;
	}
	public String getCreationUser() {
		return creationUser;
	}
	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}
	public String getChangeUser() {
		return changeUser;
	}
	public void setChangeUser(String changeUser) {
		this.changeUser = changeUser;
	}
	public String getInactivationUser() {
		return inactivationUser;
	}
	public void setInactivationUser(String inactivationUser) {
		this.inactivationUser = inactivationUser;
	}
	public TODateRangeFilter getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(TODateRangeFilter creationDate) {
		this.creationDate = creationDate;
	}
	public TODateRangeFilter getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(TODateRangeFilter changeDate) {
		this.changeDate = changeDate;
	}
	public TODateRangeFilter getInactivationDate() {
		return inactivationDate;
	}
	public void setInactivationDate(TODateRangeFilter inactivationDate) {
		this.inactivationDate = inactivationDate;
	}
	
}
