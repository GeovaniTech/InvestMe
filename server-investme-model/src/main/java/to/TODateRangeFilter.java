package to;

import java.io.Serializable;
import java.util.Date;

public class TODateRangeFilter implements Serializable {

	private static final long serialVersionUID = 8557163418920603755L;

	private Date from;
	private Date to;
	
	//Getters And Setters
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
}
