package manter.expenditure;

import javax.ejb.Local;

@Local
public interface IManterExpenditureSBean {
	public Double spents();
	public Double recreation();
	public Double market();
	public Double ifood();
	public Double others();
}