package security.wildfly;

public class WildflyConfigs {
	public final static String appConfigsPath = getBaseUrl() + "modules/br/com/devpree/investme/config/configs.properties";
	
	private static String getBaseUrl() {
		String base = System.getProperty("user.dir");
		
		if(base.equals("/")) {
			return "/opt/wf-30/";
		}
		
		return base.substring(0, base.length() -3);
	}
}
