package br.com.devpree.investme.api.common;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractObject {
	@Column(name = "creationuser")
    private String creationUser;
	
	@Column(name = "creationdate")
    private Date creationDate;

	@Column(name = "changeuser")
    private String changeUser;

	@Column(name = "changedate")
    private Date changeDate;

	@Column(name = "inactivationuser")
    private String inactivationUser;

	@Column(name = "inactivationdate")
    private Date inactivationDate;

    // Getters and Setters
	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getChangeUser() {
		return changeUser;
	}

	public void setChangeUser(String changeUser) {
		this.changeUser = changeUser;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public String getInactivationUser() {
		return inactivationUser;
	}

	public void setInactivationUser(String inactivationUser) {
		this.inactivationUser = inactivationUser;
	}

	public Date getInactivationDate() {
		return inactivationDate;
	}

	public void setInactivationDate(Date inactivationDate) {
		this.inactivationDate = inactivationDate;
	}
    
}
