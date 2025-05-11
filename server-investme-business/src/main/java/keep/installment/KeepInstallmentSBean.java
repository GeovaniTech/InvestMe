package keep.installment;

import abstracts.AbstractKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import model.Installment;
import to.installment.TOInstallment;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepInstallmentSBean extends AbstractKeep<Installment, TOInstallment> implements IKeepInstallmentSBean, IKeepInstallmentRemoteSBean{

	public KeepInstallmentSBean() {
		super(Installment.class, TOInstallment.class);
	}

	@Override
	public void save(TOInstallment installment) throws Exception {
		Installment model = this.convertToModel(installment);
		this.getEntityManager().persist(model);
		this.getEntityManager().flush();
		
		installment.setId(model.getId());
	}
}
