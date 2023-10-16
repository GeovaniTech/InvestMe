package bean;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import manter.reports.transansaction.ManterReportTransaction;
import model.Transaction;
import to.TOFile;
import utils.AbstractBean;
import utils.FileDownload;

@Named("MBReportTransaction")
@ViewScoped
public class MBReportTransaction extends AbstractBean {
	private static final long serialVersionUID = -7067404891799532575L;
	
	private ManterReportTransaction manterReport;
	private String reportType;

	public MBReportTransaction() {
		this.manterReport = new ManterReportTransaction();
	}
	
	public void generateReport() {
		if(this.getReportType() != null
			&& !this.getReportType().equals("")) {
			
			try {
				List<Transaction> transactions = this.manterReport.getData(reportType);
				
				if(transactions != null && transactions.size() > 0) {
					InputStream file = null;
					
					if(reportType == "Investment") {
						file = getClass().getResourceAsStream("/sheets/investmentsBase.xlsx");
					} else {
						file = getClass().getResourceAsStream("/sheets/expendituresBase.xlsx");
					}
					
					try (Workbook workbook = new XSSFWorkbook(file)) {
						Sheet sheet = workbook.getSheetAt(0);
						
						int rowNum = 21;
						
						for(Transaction t : transactions) {
							Row row = sheet.getRow(rowNum += 1);
							
							row.getCell(0).setCellValue(t.getActive());
							row.getCell(3).setCellValue(t.getPrice());
							row.getCell(6).setCellValue(t.getAmount());
							row.getCell(9).setCellValue(t.getDate());
							row.getCell(14).setCellValue(t.getTypeActive().getName());
						}
						
						ByteArrayOutputStream baos = new ByteArrayOutputStream();

						try {
						    workbook.write(baos);
						} finally {
						    baos.close();
						}
						
						TOFile toFile = new TOFile();
						
						toFile.setName("report.xlsx");
						toFile.setBytes(baos.toByteArray());
						
						FileDownload.download(toFile);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
		
			}
			
		} else {
	
		}
	}
	
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public ManterReportTransaction getManterReport() {
		return manterReport;
	}
	public void setManterReport(ManterReportTransaction manterReport) {
		this.manterReport = manterReport;
	}
}
