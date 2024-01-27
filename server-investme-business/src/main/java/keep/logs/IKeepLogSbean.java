package keep.logs;

import java.util.List;

import jakarta.ejb.Local;
import to.logs.TOFilterLog;
import to.logs.TOLog;

@Local
public interface IKeepLogSbean {
	public void save(TOLog log);
	public Integer count(TOFilterLog filter);
	public List<TOLog> list(TOFilterLog filter);
}
