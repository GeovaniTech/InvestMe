package manter.client;

import javax.ejb.Local;

@Local
public interface IManterClientSBean {
	public boolean logar(String email, String password);
}
