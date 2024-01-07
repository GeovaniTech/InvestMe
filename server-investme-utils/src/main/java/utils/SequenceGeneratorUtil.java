package utils;

import java.util.Random;

public class SequenceGeneratorUtil {
	public static String randomAlphabeticSequence(int lenght) {
		String alphabetic = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		String sequence = "";
		Random random = new Random();
		
		for(int i = 0; i < lenght ; i++) {
			int index = random.nextInt(alphabetic.length());
			sequence += alphabetic.charAt(index);
		}
		
		return sequence;
	}
}
