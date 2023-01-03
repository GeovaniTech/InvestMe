package manter.reports.transansaction;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import model.Transaction;
import utils.AbstractManter;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ManterReportTransaction extends AbstractManter implements IManterReportTransactionSBean, IManterReportTransactionSBeanRemote {

	@Override
	public List<Transaction> getData(String reportType) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT T FROM ").append(Transaction.class.getName()).append(" T ");
		sql.append(" WHERE T.nameClient = :client");
		sql.append(" AND T.typeTransaction = :typeTransaction ");
		
		List<Transaction> transactions = em.createQuery(sql.toString(), Transaction.class)
										 .setParameter("client", getClient().getName())
										 .setParameter("typeTransaction", reportType)
										 .getResultList();
		
		return transactions;
	}
}
