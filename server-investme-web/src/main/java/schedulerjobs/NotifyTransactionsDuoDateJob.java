package schedulerjobs;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import abstracts.AbstractJob;
import enums.EnumLogCategory;
import jakarta.enterprise.inject.spi.CDI;
import keep.client.IKeepClientSBean;
import keep.transaction.IKeepTransactionSBean;
import to.client.TOClient;
import to.transaction.TOTransaction;
import utils.EmailUtil;
import utils.MessageUtil;

public class NotifyTransactionsDuoDateJob extends AbstractJob implements Job {
	public static final String JOB_IDENTIFICATION = "NotifyTransactionsDuoDateJob_ID";

	private IKeepTransactionSBean transactionSBean;
	private IKeepClientSBean clientSBean;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {		
		try {			
			Map<String, List<TOTransaction>> pendingByUser = this.getTransactionSBean().listPendingPaymentTransactions().stream()
		            .collect(Collectors.groupingBy(TOTransaction::getCreationUser));
			
			pendingByUser.forEach((clientEmail, transactionList) -> {
				TOClient client = this.getClientSBean().findByEmail(clientEmail);
				Locale locale = new Locale(client.getAppConfig().getLanguage());
				
				String title = MessageUtil.getMessageFromProperties("job.pending.transactions.description.title", locale);
				
				StringBuilder description = new StringBuilder();
				 
				description.append("<h2>");
				description.append(MessageUtil.getMessageFromProperties("job.pending.transactions.description.header", locale));
				description.append("<h2/>");
				description.append("<p>");
				description.append(MessageUtil.getMessageFromProperties("job.pending.transactions.description.greeting", locale));
				description.append("</p>");
				description.append("<p>");
				description.append(MessageUtil.getMessageFromProperties("job.pending.transactions.description.message", locale));
				description.append("</p>");
				
				description.append("<ul>");
				
				transactionList.forEach(transaction -> {
					description.append("<li>" + transaction.getActive() + "</li>");
				});
				
				description.append("</ul>");
				
				description.append("<p><a href=https://www.devpree.com.br/investme/login>");
				description.append(MessageUtil.getMessageFromProperties("job.pending.transactions.description.link", locale));
				description.append("</a></p>");
				description.append("<p>");
				description.append(MessageUtil.getMessageFromProperties("job.pending.transactions.description.unsubscribe", locale));
				description.append("</p>");
				description.append(MessageUtil.getMessageFromProperties("job.pending.transactions.description.signature", locale));
				
				try {
					EmailUtil.sendMail(clientEmail, title, description.toString(), null, false);
				} catch (Exception e) {
					handleException(e, EnumLogCategory.JOB_PENDING_TRANSACTIONS_EMAIL, JOB_IDENTIFICATION);
				}
	        });
		} catch (Exception e) {
			handleException(e, EnumLogCategory.JOB_PENDING_TRANSACTIONS_EMAIL, JOB_IDENTIFICATION);
		}	
	}

	public IKeepTransactionSBean getTransactionSBean() {
		if (transactionSBean == null) {
			transactionSBean = CDI.current().select(IKeepTransactionSBean.class).get();
		}
		
		return transactionSBean;
	}

	public IKeepClientSBean getClientSBean() {
		if (clientSBean == null) {
			clientSBean = CDI.current().select(IKeepClientSBean.class).get();
		}
		
		return clientSBean;
	}
}
