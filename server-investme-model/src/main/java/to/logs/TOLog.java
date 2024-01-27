package to.logs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

import abstracts.AbstractTOObject;
import enums.EnumLogCategory;
import enums.EnumLogType;

public class TOLog extends AbstractTOObject {
	
	private static final long serialVersionUID = 6225045020558721738L;

	private String stack;
	private EnumLogCategory category;
	private EnumLogType type;
	private String ip;
	
	public TOLog() {
		this.setCreationDate(new Date());
		this.setIp(this.getUserPublicIpAddress());
		this.setCreationUser("None");
	}
	
	private String getUserPublicIpAddress() {
		try {
			URL whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

			return in.readLine();
		} catch (Exception e) {
			return "Error trying to find IP";	
		}
	}

	public String getStack() {
		return stack;
	}
	public void setStack(String stack) {
		this.stack = stack;
	}
	public EnumLogCategory getCategory() {
		return category;
	}
	public void setCategory(EnumLogCategory category) {
		this.category = category;
	}
	public EnumLogType getType() {
		return type;
	}
	public void setType(EnumLogType type) {
		this.type = type;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
