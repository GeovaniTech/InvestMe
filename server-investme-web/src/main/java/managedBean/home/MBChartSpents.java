package managedBean.home;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

import abstracts.AbstractMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.transaction.IKeepTransactionSBean;
import utils.MessageUtil;

@Named(MBChartSpents.MANAGED_BEAN_NAME)
@ViewScoped
public class MBChartSpents extends AbstractMBean {

	private static final long serialVersionUID = 7671190281894410973L;
	public static final String MANAGED_BEAN_NAME = "MBChartSpents";
	
	private List<Integer> years = new ArrayList<Integer>();
	private List<Integer> months = new ArrayList<Integer>();
	private Integer yearSelected;
	
	private LineChartModel lineModel;
	
	@EJB
	private IKeepTransactionSBean transactionSBean;
	
	@PostConstruct
	public void init() {
		this.createMonths();
		this.setYears(this.getTransactionSBean().getYearsFromTransaction());
		this.selectLastYear();
		this.createChartSpents();
	}
	
	private void selectLastYear() {
		if(this.getYears() != null && this.getYears().size() > 0) {
			this.setYearSelected(this.getYears().get(0));
		}
	}
	
	private void createMonths() {
		this.getMonths().add(1);
		this.getMonths().add(2);
		this.getMonths().add(3);
		this.getMonths().add(4);
		this.getMonths().add(5);
		this.getMonths().add(6);
		this.getMonths().add(7);
		this.getMonths().add(8);
		this.getMonths().add(9);
		this.getMonths().add(10);
		this.getMonths().add(11);
		this.getMonths().add(12);
	}
	
	public void createChartSpents() {
		this.setLineModel(new LineChartModel());
		
		ChartData data = new ChartData();
		LineChartDataSet investments = new LineChartDataSet();
		LineChartDataSet expenditures = new LineChartDataSet();
		
		List<Object> valuesInvestments = new ArrayList<>();
		List<Object> valuesExpenditures = new ArrayList<>();
		
		for(Integer value : this.getMonths()) {
			valuesInvestments.add(this.getTransactionSBean().getTotalInvestmentsChartByYear(this.getYearSelected(), value));
		}

		for(Integer value : this.getMonths()) {
			valuesExpenditures.add(this.getTransactionSBean().getTotalExpensesChartByYear(this.getYearSelected(), value));
		}
		
		investments.setData(valuesInvestments);
		investments.setFill(false);
		investments.setLabel(MessageUtil.getMessageFromProperties("investments"));
		investments.setBorderColor("rgb(75, 192, 192)"); // Light green
		investments.setTension(0.1);
		
		expenditures.setData(valuesExpenditures);
		expenditures.setFill(false);
		expenditures.setLabel(MessageUtil.getMessageFromProperties("expenditures"));
		expenditures.setBorderColor("rgb(255, 0, 0)"); // Red
		expenditures.setTension(0.1);
		
        data.addChartDataSet(investments);
        data.addChartDataSet(expenditures);
        
        List<String> labels = new ArrayList<>();
        labels.add(MessageUtil.getMessageFromProperties("january"));
        labels.add(MessageUtil.getMessageFromProperties("february"));
        labels.add(MessageUtil.getMessageFromProperties("march"));
        labels.add(MessageUtil.getMessageFromProperties("april"));
        labels.add(MessageUtil.getMessageFromProperties("may"));
        labels.add(MessageUtil.getMessageFromProperties("june"));
        labels.add(MessageUtil.getMessageFromProperties("july"));
        labels.add(MessageUtil.getMessageFromProperties("august"));
        labels.add(MessageUtil.getMessageFromProperties("september"));
        labels.add(MessageUtil.getMessageFromProperties("october"));
        labels.add(MessageUtil.getMessageFromProperties("november"));
        labels.add(MessageUtil.getMessageFromProperties("december"));

        data.setLabels(labels);
        
        //Options
        LineChartOptions options = new LineChartOptions();        
        Title title = new Title();
        title.setDisplay(true);
        title.setText(MessageUtil.getMessageFromProperties("expenses_by_type_of_transaction"));
        options.setTitle(title);
        
        this.getLineModel().setOptions(options);
        this.getLineModel().setData(data);
	}

	// Getters and Setters
	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public IKeepTransactionSBean getTransactionSBean() {
		return transactionSBean;
	}

	public void setTransactionSBean(IKeepTransactionSBean transactionSBean) {
		this.transactionSBean = transactionSBean;
	}

	public Integer getYearSelected() {
		return yearSelected;
	}

	public void setYearSelected(Integer yearSelected) {
		this.yearSelected = yearSelected;
	}

	public List<Integer> getMonths() {
		return months;
	}

	public void setMonths(List<Integer> months) {
		this.months = months;
	}
	
}
