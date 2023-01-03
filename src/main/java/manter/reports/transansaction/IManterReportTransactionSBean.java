package manter.reports.transansaction;

import java.util.List;

import javax.ejb.Local;

import model.Transaction;

@Local
public interface IManterReportTransactionSBean {
	public List<Transaction> getData(String reportType);
}
