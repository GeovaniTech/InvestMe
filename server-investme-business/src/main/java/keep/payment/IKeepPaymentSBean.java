package keep.payment;

import java.util.List;

import jakarta.ejb.Local;
import to.payment.TOFilterPayment;
import to.payment.TOPayment;

@Local
public interface IKeepPaymentSBean {
	public void save(TOPayment payment);
	public void change(TOPayment payment);
	public void remove(TOPayment payment);
	public TOPayment findById(int id);
	public Integer getCount(TOFilterPayment filter);
	public List<TOPayment> search(TOFilterPayment filter);
	public List<TOPayment> listAll();
	public void deletePaymentsFromUser() throws Exception;
}
