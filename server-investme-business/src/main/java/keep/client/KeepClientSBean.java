package keep.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import abstracts.AbstractKeep;
import enums.EnumLogCategory;
import enums.EnumLogType;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import keep.appConfig.IKeepAppConfigSBean;
import keep.category.IKeepCategorySBean;
import keep.logs.IKeepLogSbean;
import keep.payment.IKeepPaymentSBean;
import keep.transaction.IKeepTransactionSBean;
import model.Client;
import query.SimpleWhere;
import to.TOParameter;
import to.category.TOCategory;
import to.client.TOClient;
import to.client.TOFilterClient;
import to.client.TOFilterLovClient;
import to.logs.TOLog;
import to.payment.TOPayment;
import to.transaction.TOTransaction;
import utils.EncryptionUtil;
import utils.JWTUtil;
import utils.MessageUtil;
import utils.RedirectURL;
import utils.StringUtil;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepClientSBean extends AbstractKeep<Client, TOClient> implements IKeepClientSBean, IKeepClientRemoteSBean {

	@EJB
	private IKeepAppConfigSBean appConfigsSBean;
	
	@EJB
	private IKeepLogSbean logSbean;
	
	@EJB
	private IKeepCategorySBean categorySBean;
	
	@EJB
	private IKeepPaymentSBean paymentSBean;
	
	@EJB
	private IKeepTransactionSBean transactionSBean;
	
	public KeepClientSBean() {
		super(Client.class, TOClient.class);
	}
	
	@Override
	public boolean save(String email, String password) {
		Client client = new Client();
		client.setEmail(email);
		client.setPassword(EncryptionUtil.encryptTextSHA(password));
		
		this.getEntityManager().persist(client);
		
		return true;
	}

	@Override
	public void save(TOClient client) {
		Client model = this.convertToModel(client);
		model.setPassword(EncryptionUtil.encryptTextSHA("123"));
		model.setChangePassword(true);
		
		this.getEntityManager().persist(model);
	}

	@Override
	public void change(TOClient client) {
		Client pattern = this.getEntityManager().find(Client.class, client.getId());
		
		if(pattern.getAppConfig() == null && client.getAppConfig() != null) {
			this.getAppConfigsSBean().save(client.getAppConfig());
			this.getEntityManager().flush();
		} else if(client.getAppConfig() != null) {
			this.getAppConfigsSBean().change(client.getAppConfig());
			this.getEntityManager().flush();
		}
		
		Client model = this.convertToModel(client);
	
		if(StringUtil.isNull(model.getEmail())) {
			model.setEmail(pattern.getEmail());
		}
		
		model.setPassword(pattern.getPassword());
		
		this.getEntityManager().merge(model);
	}

	@Override
	public void remove(TOClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean verifyClient(String email) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C ");
		sql.append(" FROM ").append(Client.class.getSimpleName()).append(" C ");
		sql.append(" WHERE C.email = :email ");
		sql.append(" AND C.creationDate IS NOT NUll ");
		sql.append(" AND C.blocked = false ");
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("email", email);
		
		if(query.getResultList().size() == 1) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean logar(String email, String password) {		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C FROM ")
			.append(Client.class.getSimpleName()).append(" C ")
			.append(" WHERE C.email = :pEmail AND C.password = :pPassword ")
			.append(" AND C.creationDate IS NOT NUll ");
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Client.class);
		query.setParameter("pEmail", email);
		query.setParameter("pPassword", EncryptionUtil.encryptTextSHA(password));
		
		if(query.getResultList().size() == 1) {
			Client client = (Client) query.getSingleResult();
			
			if(client.isBlocked()) {
				MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("user_blocked"), FacesMessage.SEVERITY_ERROR);
				
				TOLog log = new TOLog();
				log.setCreationUser(email);
				log.setStack("User is blocked. Credentials: \n Email: " + email);
				log.setCategory(EnumLogCategory.USER_BLOCKED_LOGIN);
				log.setType(EnumLogType.WARN);
				
				this.getLogSbean().save(log);
				return false;
			}
			
			String encrypedEmail = EncryptionUtil.encryptNormalText(client.getEmail());
			
			if(client.isChangePassword()) {
				String token = JWTUtil.generateToken("newPassword", encrypedEmail);
				
				RedirectURL.redirectTo("/investme/newpassword/" + token);
				
				return false;
			}
			
			TOClient to = this.getTOClient(client);
			
			if(to.getLastLogin() == null) {
				this.createDefaultEntitys(to);
			}
			
			to.setLastLogin(new Date());
			
			this.change(to);
			
			this.getSession().setAttribute("client", to);
			
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
			Cookie userCookie = new Cookie("userSession", EncryptionUtil.encryptNormalText(client.getEmail()));
			userCookie.setMaxAge(60*60*24*30);
			userCookie.setPath("/investme");
			
			response.addCookie(userCookie);

			return true;
		}
		
		TOLog log = new TOLog();
		log.setCreationUser(email);
		
		String message = "Invalid Credentials. Credentials: Email:" + email + " Password: " + EncryptionUtil.encryptTextSHA(password);
		log.setStack(message);
		log.setCategory(EnumLogCategory.INVALID_LOGIN);
		log.setType(EnumLogType.WARN);
		
		this.getLogSbean().save(log);
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("user_or_password_incorrect"), FacesMessage.SEVERITY_ERROR);
		
		return false;
	}

	@Override
	public TOClient findByEmail(String email) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C FROM ")
			.append(Client.class.getSimpleName()).append(" C ")
			.append(" WHERE C.email  = :pEmail ");
		
		Client client = (Client) this.getEntityManager()
				.createQuery(sql.toString())
				.setParameter("pEmail", email)
				.getSingleResult();
		
		
		TOClient to = this.getTOClient(client);
				
		return to;
	}

	@Override
	public TOClient findById(int id) {
		Client client = this.getEntityManager().find(Client.class, id);
		
		if(client != null) {
			TOClient to = this.convertToDTO(client);
			
			return to;
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TOClient> list(TOFilterClient filter) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ")
			.append(Client.class.getSimpleName()).append(" C ");
		
		List<TOParameter> params = new ArrayList<TOParameter>();
		
		sql.append(this.getWhereListClients(filter, params));
		sql.append(this.getOrderByListClients());
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Client.class);
		query.setFirstResult(filter.getFirstResult());
		query.setMaxResults(filter.getMaxResults());
		setParameters(query, params);
				
		return this.convertModelResults(query.getResultList());
	}
	
	@Override
	public int countClient(TOFilterClient filter) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(C.id) FROM ")
			.append(Client.class.getSimpleName()).append(" C ");
		
		List<TOParameter> params = new  ArrayList<TOParameter>();
		
		sql.append(this.getWhereListClients(filter, params));
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Number.class);
		setParameters(query, params);
		
		Number value = (Number) query.getSingleResult();
		
		return value.intValue();
	}

	@Override
	public List<TOClient> listClientsLov(TOFilterLovClient filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNewPassword(String email, String password) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" UPDATE ")
			.append(Client.class.getSimpleName()).append(" C ")
			.append(" SET C.password = :pPassword, ")
			.append(" C.changePassword = false ")
			.append(" WHERE  C.email = :pEmail ");
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("pPassword", EncryptionUtil.encryptTextSHA(password));
		query.setParameter("pEmail", email);
		
		query.executeUpdate();
		
		if(this.logar(email, password)) {
			RedirectURL.redirectTo("/investme/client/wallet");
		}
	}

	@Override
	public void finishRegister(String email) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT C FROM ")
			.append(Client.class.getSimpleName()).append(" C ")
			.append(" WHERE C.email  = :pEmail ")
			.append(" AND C.creationDate IS NULL ");
		
		Query query = this.getEntityManager().createQuery(sql.toString());
		query.setParameter("pEmail", email);
		
		if(query.getResultList().size() == 1) {
			Client client = (Client) query.getSingleResult();
			
			client.setCreationDate(new Date());
			client.setCreationUser("Investme System");
			client.setSecurityLevel("client");
			
			this.getEntityManager().merge(client); 
			
			RedirectURL.redirectTo("/investme/login/finished");
			
			return;
		}
		
		MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("user_already_completed_registration"), FacesMessage.SEVERITY_ERROR);
	}
	
	public String getWhereListClients(TOFilterClient filter, List<TOParameter> params) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" WHERE C.creationDate IS NOT NULL ");
		sql.append(SimpleWhere.queryFilter("C.email", filter.getEmail()));
		sql.append(SimpleWhere.queryFilter("C.phoneNumber", filter.getPhoneNumber()));
		sql.append(SimpleWhere.queryFilterDateRange("C.lastLogin", filter.getLastLogin(), TemporalType.TIMESTAMP, params));
		
		if(StringUtil.isNotNull(filter.getSecurityLevel())) {
			sql.append(" AND C.securityLevel = :securityLevel ");
			params.add(new TOParameter("securityLevel", filter.getSecurityLevel()));
		}
		
		if(filter.getBlocked() != null) {
			sql.append(" AND C.blocked = :blocked ");
			params.add(new TOParameter("blocked", filter.getBlocked()));
		}
		
		return sql.toString();
	}
	
	public String getOrderByListClients() {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" ORDER BY C.email ASC ");
		
		return sql.toString();
	}

	@Override
	public boolean existsClientByEmail(String email) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ")
			.append(Client.class.getSimpleName()).append(" C ")
			.append(" WHERE C.email = :pEmail ");
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Client.class);
		query.setParameter("pEmail", email);
		
		return query.getResultList().size() == 1;
	}
	
	@Override
	public boolean existsClientByEmail(String email, int clientId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ")
			.append(Client.class.getSimpleName()).append(" C ")
			.append(" WHERE C.email = :pEmail ")
			.append(" AND C.id != :pClientId ");
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Client.class);
		query.setParameter("pEmail", email);
		query.setParameter("pClientId", clientId);
		
		return query.getResultList().size() == 1;
	}
	
	private void createDefaultEntitys(TOClient client) {
		String user = client.getEmail();
		
		TOPayment money = new TOPayment();
		money.setName(this.getLabel("money"));
		money.setCreationDate(new Date());
		money.setCreationUser(user);
		
		TOPayment debitCard = new TOPayment();
		debitCard.setName(this.getLabel("debit_card"));
		debitCard.setCreationDate(new Date());
		debitCard.setCreationUser(user);
		
		TOPayment creditCard = new TOPayment();
		creditCard.setName(this.getLabel("credit_card"));
		creditCard.setCreationDate(new Date());
		creditCard.setCreationUser(user);
		
		TOPayment pix = new TOPayment();
		pix.setName(this.getLabel("pix"));
		pix.setCreationDate(new Date());
		pix.setCreationUser(user);
		
		TOPayment ticket = new TOPayment();
		ticket.setName(this.getLabel("ticket"));
		ticket.setCreationDate(new Date());
		ticket.setCreationUser(user);
		
		this.getPaymentSBean().save(money);
		this.getPaymentSBean().save(debitCard);
		this.getPaymentSBean().save(creditCard);
		this.getPaymentSBean().save(pix);
		this.getPaymentSBean().save(ticket);
		
		TOCategory food = new TOCategory();
		food.setName(this.getLabel("food"));
		food.setType("expense");
		food.setCreationDate(new Date());
		food.setCreationUser(user);
		
		TOCategory transport = new TOCategory();
		transport.setName(this.getLabel("transport"));
		transport.setType("expense");
		transport.setCreationDate(new Date());
		transport.setCreationUser(user);
		
		TOCategory health = new TOCategory();
		health.setName(this.getLabel("health"));
		health.setType("expense");
		health.setCreationDate(new Date());
		health.setCreationUser(user);
		
		TOCategory education = new TOCategory();
		education.setName(this.getLabel("education"));
		education.setType("expense");
		education.setCreationDate(new Date());
		education.setCreationUser(user);
		
		TOCategory others = new TOCategory();
		others.setName(this.getLabel("others"));
		others.setType("expense");
		others.setCreationDate(new Date());
		others.setCreationUser(user);
		
		TOCategory emergencyMoney = new TOCategory();
		emergencyMoney.setName(this.getLabel("emergency_money"));
		emergencyMoney.setType("investment");
		emergencyMoney.setCreationDate(new Date());
		emergencyMoney.setCreationUser(user);
		
		TOCategory actions = new TOCategory();
		actions.setName(this.getLabel("actions"));
		actions.setType("investment");
		actions.setCreationDate(new Date());
		actions.setCreationUser(user);
		
		TOCategory cdi = new TOCategory();
		cdi.setName(this.getLabel("cdi"));
		cdi.setType("investment");
		cdi.setCreationDate(new Date());
		cdi.setCreationUser(user);
		
		TOCategory fiis = new TOCategory();
		fiis.setName(this.getLabel("fiis"));
		fiis.setType("investment");
		fiis.setCreationDate(new Date());
		fiis.setCreationUser(user);
		
		TOCategory bdrs = new TOCategory();
		bdrs.setName(this.getLabel("bdrs"));
		bdrs.setType("investment");
		bdrs.setCreationDate(new Date());
		bdrs.setCreationUser(user);
		
		TOCategory criptocurrencys = new TOCategory();
		criptocurrencys.setName(this.getLabel("criptocurrencys"));
		criptocurrencys.setType("investment");
		criptocurrencys.setCreationDate(new Date());
		criptocurrencys.setCreationUser(user);
		
		// Expenses
		this.getCategorySBean().save(food);
		this.getCategorySBean().save(transport);
		this.getCategorySBean().save(health);
		this.getCategorySBean().save(education);
		this.getCategorySBean().save(others);
		
		// Investments
		this.getCategorySBean().save(emergencyMoney);
		this.getCategorySBean().save(actions);
		this.getCategorySBean().save(fiis);
		this.getCategorySBean().save(cdi);
		this.getCategorySBean().save(bdrs);
		this.getCategorySBean().save(criptocurrencys);
		
		TOTransaction nvdc34 = new TOTransaction();
		nvdc34.setActive("NVDC34 " + this.getLabel("example_you_can_delete"));
		nvdc34.setPrice(90.51);
		nvdc34.setAmount(3);
		nvdc34.setCategory(actions);
		nvdc34.setPayment(pix);
		nvdc34.setDatePurchase(new Date());
		nvdc34.setCreationDate(new Date());
		nvdc34.setCreationUser(user);
		nvdc34.setClient(client);
		
		TOTransaction tsla32 = new TOTransaction();
		tsla32.setActive("TSLA32 " + this.getLabel("example_you_can_delete"));
		tsla32.setPrice(25.43);
		tsla32.setAmount(7);
		tsla32.setCategory(actions);
		tsla32.setPayment(creditCard);
		tsla32.setDatePurchase(new Date());
		tsla32.setCreationDate(new Date());
		tsla32.setCreationUser(user);
		tsla32.setClient(client);
		
		TOTransaction em = new TOTransaction();
		em.setActive(this.getLabel("emergency_money") + " " + this.getLabel("example_you_can_delete"));
		em.setPrice(698.0);
		em.setAmount(1);
		em.setCategory(emergencyMoney);
		em.setPayment(pix);
		em.setDatePurchase(new Date());
		em.setCreationDate(new Date());
		em.setCreationUser(user);
		em.setClient(client);
		
		TOTransaction pizza = new TOTransaction();
		pizza.setActive("Pizza " + this.getLabel("example_you_can_delete"));
		pizza.setPrice(85.0);
		pizza.setAmount(1);
		pizza.setCategory(food);
		pizza.setPayment(ticket);
		pizza.setDatePurchase(new Date());
		pizza.setCreationDate(new Date());
		pizza.setCreationUser(user);
		pizza.setClient(client);
		
		TOTransaction uber = new TOTransaction();
		uber.setActive(this.getLabel("uber_to_work") + " " + this.getLabel("example_you_can_delete"));
		uber.setPrice(21.0);
		uber.setAmount(1);
		uber.setCategory(transport);
		uber.setPayment(money);
		uber.setDatePurchase(new Date());
		uber.setCreationDate(new Date());
		uber.setCreationUser(user);
		uber.setClient(client);
		
		this.getTransactionSBean().save(nvdc34);
		this.getTransactionSBean().save(tsla32);
		this.getTransactionSBean().save(em);
		this.getTransactionSBean().save(pizza);
		this.getTransactionSBean().save(uber);
	}
	
	@Override
	public void deleteAccount() throws Exception {
		try {			
			this.getTransactionSBean().deleteTransactionsFromUser();
			this.getPaymentSBean().deletePaymentsFromUser();
			this.getCategorySBean().deleteCategoriesFromUser();
			
			StringBuilder sql = new StringBuilder();
			sql.append(" DELETE FROM ").append(Client.class.getSimpleName()).append(" C ");
			sql.append(" WHERE C.email = :email ");
			
			Query query = this.getEntityManager().createQuery(sql.toString());
			query.setParameter("email", this.getClientSession().getEmail());
			
			query.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		}		
	}
	
	private TOClient getTOClient(Client client) {
		TOClient to = this.convertToDTO(client);
		
		return to;
	}

	// Getters and Setters
	public IKeepAppConfigSBean getAppConfigsSBean() {
		return appConfigsSBean;
	}

	public void setAppConfigsSBean(IKeepAppConfigSBean appConfigsSBean) {
		this.appConfigsSBean = appConfigsSBean;
	}

	public IKeepLogSbean getLogSbean() {
		return logSbean;
	}

	public void setLogSbean(IKeepLogSbean logSbean) {
		this.logSbean = logSbean;
	}

	public IKeepCategorySBean getCategorySBean() {
		return categorySBean;
	}

	public void setCategorySBean(IKeepCategorySBean categorySBean) {
		this.categorySBean = categorySBean;
	}

	public IKeepPaymentSBean getPaymentSBean() {
		return paymentSBean;
	}

	public void setPaymentSBean(IKeepPaymentSBean paymentSBean) {
		this.paymentSBean = paymentSBean;
	}

	public IKeepTransactionSBean getTransactionSBean() {
		return transactionSBean;
	}

	public void setTransactionSBean(IKeepTransactionSBean transactionSBean) {
		this.transactionSBean = transactionSBean;
	}
	
}
