package keep.appConfig;

import jakarta.ejb.Local;
import to.appconfigs.TOAppConfig;

@Local
public interface IKeepAppConfigSBean {
	public void save(TOAppConfig appConfig);
	public void change(TOAppConfig appConfig);
}
