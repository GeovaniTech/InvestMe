package keep.appConfig;

import abstracts.AbstractKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import model.AppConfig;
import to.appconfigs.TOAppConfig;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepAppConfigSBean extends AbstractKeep<AppConfig, TOAppConfig>implements IKeepAppConfigSBean, IKeepAppConfigSBeanRemote {
	public KeepAppConfigSBean() {
		super(AppConfig.class, TOAppConfig.class);
	}

	@Override
	public void save(TOAppConfig appConfig) {
		AppConfig model = this.convertToModel(appConfig);
		this.getEntityManager().persist(model);
		this.getEntityManager().flush();
	}

	@Override
	public void change(TOAppConfig appConfig) {
		AppConfig model = this.convertToModel(appConfig);
		this.getEntityManager().merge(model);
		this.getEntityManager().flush();
	}
}
