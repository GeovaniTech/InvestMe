package keep.client;

import java.util.List;

import jakarta.ejb.Local;
import to.client.TOClient;
import to.client.TOFilterClient;
import to.client.TOFilterLovClient;

@Local
public interface IKeepClientSBean {
	public boolean save(String email, String password);
	public void save(TOClient client);
	public void change(TOClient client);
	public void remove(TOClient client);
	public int countClient(TOFilterClient filter);
	public boolean verifyClient(String email);
	public boolean logar(String email, String password);
	public boolean existsClientByEmail(String email);
	public boolean existsClientByEmail(String email, int clientId);
	public TOClient findByEmail(String email);
	public TOClient findById(int id);
	public List<TOClient> list(TOFilterClient filter);
	public List<TOClient> listClientsLov(TOFilterLovClient filter);
	public void setNewPassword(String email, String password);
	public void finishRegister(String email);
}
