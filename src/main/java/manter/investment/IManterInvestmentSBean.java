package manter.investment;

import javax.ejb.Local;

@Local
public interface IManterInvestmentSBean {
	public Double spents();
	public Double actions();
	public Double fiis();
	public Double fixedIncome();
	public Double criptocurrencys();
}

