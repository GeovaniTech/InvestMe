package abstracts;

import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;

import enums.EnumLogCategory;
import enums.EnumLogType;
import jakarta.enterprise.inject.spi.CDI;
import keep.logs.IKeepLogSbean;
import to.logs.TOLog;

public class AbstractJob {
	private IKeepLogSbean logSbean;
	
	public void handleException(Exception e, EnumLogCategory category, String jobId) {
		e.printStackTrace();
		
		TOLog log = new TOLog();
		
		log.setCreationUser(jobId);
		log.setCreationDate(new Date());
		log.setStack(ExceptionUtils.getStackTrace(e));
		log.setType(EnumLogType.EXCEPTION);
		log.setCategory(category);
		
		this.saveLog(log);
	}
	
	public void saveLog(TOLog log) {
		this.getLogSBean().save(log);
	}
	
	protected IKeepLogSbean getLogSBean() {
		if (logSbean == null) {
			 logSbean = CDI.current().select(IKeepLogSbean.class).get();
		}
		
		return logSbean;
	}
}
