package utils;

public class StringUtil {
	public static boolean isNotNull(String value) {
		if(value != null && !value.trim().equals("")) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isNull(String value) {
		if(value == null || value.trim().equals("")) {
			return true;
		}
		
		return false;
	}
}
