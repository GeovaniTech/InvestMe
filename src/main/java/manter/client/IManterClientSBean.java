package manter.client;

import javax.ejb.Local;

import to.TOClient;

@Local
public interface IManterClientSBean {
	public boolean validateAccess(TOClient toUser, String password);
}
