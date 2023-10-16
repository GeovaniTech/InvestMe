package manter.client;

import jakarta.ejb.Local;

@Local
public interface IManterClientSBean {
	public boolean logar(String email, String password);
	public void register(String email, String password);
}
