package abstracts;

import java.io.Serializable;
import java.util.Date;

public class AbstractTOObject implements Serializable {
    private static final long serialVersionUID = -5842360621544505239L;
	
    private int id;
    private String creationUser;
    private Date creationDate;
    
    private String changeUser;
    private Date changeDate;
    
    private String inactivationUser;
    private Date inactivationDate;
	
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
