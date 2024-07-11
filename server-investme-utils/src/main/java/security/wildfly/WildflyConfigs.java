package security.wildfly;

import java.io.FileInputStream;
import java.util.Properties;

public class WildflyConfigs {
	public final static String appConfigsPath = getBaseUrl() + "modules/br/com/devpree/investme/config/configs.properties";
	private static Properties appConfigs;
	
	private static String getBaseUrl() {
		String base = System.getProperty("user.dir");
		
		if(base.equals("/")) {
			return "/opt/wf-30/";
		}
		
		return base.substring(0, base.length() -3);
	}
	
	public static String getConfig(String key) throws Exception {
		if (appConfigs == null) {
			appConfigs = new Properties();
			
	    	try (FileInputStream fis = new FileInputStream(WildflyConfigs.appConfigsPath)) {
	    		appConfigs.load(fis);
	    	}
		}
		
    	return appConfigs.getProperty(key);
	}
	
}
