package manter.investment;

import jakarta.ejb.Local;

@Local
public interface IManterInvestmentSBean {
	public Double spents();
	public Double actions();
	public Double fiis();
	public Double fixedIncome();
	public Double criptocurrencys();
}

