package interfaces;

import java.util.List;

public interface GenericDAO<to> {
	public void save(to to);
	public void change(to to);
	public void remove(to to);
	public to findById(int id);
	public List<to> list();
	public List<to> list(String specificType);
}
