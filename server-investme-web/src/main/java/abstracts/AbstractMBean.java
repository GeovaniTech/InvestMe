package abstracts;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;

import enums.EnumLogCategory;
import enums.EnumLogType;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.FacesMessage.Severity;
import jakarta.transaction.RollbackException;
import managedBean.logs.MBLog;
import to.logs.TOLog;
import utils.MessageUtil;

public abstract class AbstractMBean extends AbstractSession implements Serializable {
	private static final long serialVersionUID = -3126954606226723860L;
	
	public void showMessageItemSaved(String itemName) {
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_item_saved", itemName), FacesMessage.SEVERITY_INFO);
	}
	
	public void showMessageItemChanged(String itemName) {
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_item_changed", itemName), FacesMessage.SEVERITY_INFO);
	}
	
	public void showMessageItemRemoved(String itemName) {
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_item_removed", itemName), FacesMessage.SEVERITY_INFO);
	}
	
	public void showMessageError(Exception e) {
		e.printStackTrace();
		
		if (e.getCause() instanceof RollbackException) {
			RollbackException rollbackException = (RollbackException) e.getCause();
			
			if (rollbackException.getCause() instanceof ConstraintViolationException) {
				MessageUtil.sendMessage(
						MessageUtil.getMessageFromProperties("msg_error_removing_entity_used_other_tables"),
						FacesMessage.SEVERITY_ERROR);
				return;
			}
		}
		
		TOLog log = new TOLog();
		
		log.setCreationUser(this.getClientSession().getEmail());
		log.setCreationDate(new Date());
		log.setStack(ExceptionUtils.getStackTrace(e));
		log.setType(EnumLogType.EXCEPTION);
		log.setCategory(EnumLogCategory.OTHER);
		
		this.saveLog(log);
		
		MessageUtil.sendMessage(e.getMessage(), FacesMessage.SEVERITY_ERROR);
	}
	
	public void saveLog(TOLog log) {
		MBLog mblog = this.getMBean(MBLog.MANAGED_BEAN_NAME);
		mblog.save(log);
	}
	
	public boolean isUserAdmin() {
		if(this.getClientSession().getSecurityLevel().equals("admin")) {
			return true;
		}
		
		return false;
	}
	
	public void addMessage(String message, Severity severity) {
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties(message), severity);
	}
	
	public String getLabel(String key) {
		return MessageUtil.getMessageFromProperties(key);
	}
}
