package to;

public class TOType {
	private int id;
	private String name;
	private String nameClient;
	private String typeTransaction;
	private Double spents;
	
	//Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameClient() {
		return nameClient;
	}
	public void setNameClient(String nameClient) {
		this.nameClient = nameClient;
	}
	public String getTypeTransaction() {
		return typeTransaction;
	}
	public void setTypeTransaction(String typeTransaction) {
		this.typeTransaction = typeTransaction;
	}
	public Double getSpents() {
		return spents;
	}
	public void setSpents(Double spents) {
		this.spents = spents;
	}
}
