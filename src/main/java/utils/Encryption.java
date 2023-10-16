package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.jasypt.util.text.BasicTextEncryptor;


public class Encryption {
	public static String encryptTextSHA(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
	}	
	
	public static String encryptNormalText(String text) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPasswordCharArray("email-text-encriptation".toCharArray());
		
		return textEncryptor.encrypt(text);
	}
	
	public static String decryptNormalText(String text) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPasswordCharArray("email-text-encriptation".toCharArray());
		
		return textEncryptor.decrypt(text);
	}
}