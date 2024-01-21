package abstracts;

import java.io.Serializable;

import org.hibernate.exception.ConstraintViolationException;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.FacesMessage.Severity;
import jakarta.transaction.RollbackException;
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
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("msg_item_removed", itemName), FacesMessage.SEVERITY_ERROR);
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

		MessageUtil.sendMessage(e.getMessage(), FacesMessage.SEVERITY_ERROR);
	}
	
	public void addMessage(String message, Severity severity) {
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties(message), severity);
	}
}
