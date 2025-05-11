package keep.installment;

import jakarta.ejb.Local;
import to.installment.TOInstallment;

@Local
public interface IKeepInstallmentSBean {
	public void save(TOInstallment installment) throws Exception;
}
