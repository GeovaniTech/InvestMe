package utils;

import java.util.Base64;

public class ImageUtil {
	public static String geRenderedImage(byte[] imageBytes) {
		return imageBytes != null ? Base64.getEncoder().encodeToString(imageBytes) : null;
	}
}
