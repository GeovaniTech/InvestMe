package interfaces;

import java.util.List;

public interface GenericDAO<to> {
	public void save(to model);
	public void change(to model);
	public void remove(to model);
	public to findById(int id);
	public List<to> list();
}
