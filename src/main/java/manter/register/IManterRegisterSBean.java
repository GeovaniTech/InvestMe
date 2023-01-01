package manter.register;

import javax.ejb.Local;

@Local
public interface IManterRegisterSBean {
	public boolean register(String user, 
						 String password, 
						 String repeatPassword);
}
