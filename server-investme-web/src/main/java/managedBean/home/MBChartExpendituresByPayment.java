package managedBean.home;

import java.util.List;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import abstracts.AbstractMBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import keep.category.IKeepCategorySBean;
import keep.transaction.IKeepTransactionSBean;
import to.charts.TOChartByPayment;
import to.transaction.TOFilterTransaction;
import utils.ColorUtil;

@Named(MBChartExpendituresByPayment.MANAGED_BEAN_NAME)
@ViewScoped
public class MBChartExpendituresByPayment extends AbstractMBean {

	private static final long serialVersionUID = 7671190281894410973L;
	public static final String MANAGED_BEAN_NAME = "MBChartExpendituresByPayment";
	
	private PieChartModel pieModel;
	private boolean hasData;
	
	@EJB
	private IKeepTransactionSBean transactionSBean;
	
	@EJB
	private IKeepCategorySBean categorySBean;
	
	@PostConstruct
	public void init() {
		this.createChartExpendituresByPayment();
	}
	
	public void createChartExpendituresByPayment() {
        this.setPieModel(new PieChartModel());
        
        ChartData data = new ChartData();
        PieChartDataSet dataSet = new PieChartDataSet();
        
        TOFilterTransaction filter = new TOFilterTransaction();
		
        try {
			filter = (TOFilterTransaction) this.getMBHome().getFilter().clone();
		} catch (CloneNotSupportedException e) {
			showMessageError(e);
		}
        
        List<TOChartByPayment> chartData = this.getTransactionSBean().getChartByPayment(filter);
        
        List<String> labels = chartData.stream().map(t -> t.getPaymentName()).toList();
        List<Number> values = chartData.stream().map(t -> t.getTotal()).toList();
        
        data.setLabels(labels);
        dataSet.setData(values);
        
        dataSet.setBackgroundColor(ColorUtil.generateRandomColors(values.size()));
        data.addChartDataSet(dataSet);
        
        this.getPieModel().setData(data);
        
        updateHasData(values);     
	}

	private void updateHasData(List<Number> values) {
		if(values.size() > 0) {
        	this.setHasData(true);
        } else {
        	this.setHasData(false);
        }
	}
	
	public MBHome getMBHome() {
		return this.getMBean(MBHome.MANAGED_BEAN_NAME);
	}
	
	// Getters and Setters
	public IKeepTransactionSBean getTransactionSBean() {
		return transactionSBean;
	}

	public void setTransactionSBean(IKeepTransactionSBean transactionSBean) {
		this.transactionSBean = transactionSBean;
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}

	public IKeepCategorySBean getCategorySBean() {
		return categorySBean;
	}

	public void setCategorySBean(IKeepCategorySBean categorySBean) {
		this.categorySBean = categorySBean;
	}

	public boolean isHasData() {
		return hasData;
	}

	public void setHasData(boolean hasData) {
		this.hasData = hasData;
	}
	
}
