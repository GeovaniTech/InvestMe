package utils;

import java.util.List;

public class ListUtil {
	public static boolean isNotNull(List list) {
		return list != null && !list.isEmpty();
	}
	
	public static boolean isNull(List list) {
		return !isNotNull(list);
	}
}
