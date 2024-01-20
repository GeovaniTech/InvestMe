package keep.transaction;

import java.util.List;

import jakarta.ejb.Local;
import to.category.TOCategory;
import to.transaction.TOFilterTransaction;
import to.transaction.TOTransaction;

@Local
public interface IKeepTransactionSBean {
	public void save(TOTransaction transaction);
	public void change(TOTransaction transaction);
	public void remove(TOTransaction transaction);
	public TOTransaction findById(int id);
	public List<TOTransaction> search(TOFilterTransaction filter);
	public Integer getCount(TOFilterTransaction filter);
	public Double getTotalSpentByCategory(TOFilterTransaction filter, TOCategory category);
	public Double getTotalByType(TOFilterTransaction filter, String type);
	public List<Integer> getYearsFromTransaction();
	public Double getTotalExpensesChartByYear(Integer year, Integer month);
	public Double getTotalInvestmentsChartByYear(Integer year, Integer month);
}
