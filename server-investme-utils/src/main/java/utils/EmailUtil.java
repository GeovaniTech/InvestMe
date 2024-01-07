package utils;


import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.faces.application.FacesMessage;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtil {
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

    public static boolean isValidEmailPattern(String email) {
        Matcher matcher = PATTERN.matcher(email);
        return matcher.matches();
    }
    
    public static void sendMail(String to, String title, String description, String success_message) {
    	try {
        	Properties properties = new Properties();
        	
        	properties.put("mail.smtp.auth", "true");
        	properties.put("mail.smtp.starttls.enable", "true");
        	properties.put("mail.smtp.host", "smtp.gmail.com");
        	properties.put("mail.smtp.port", "587");
        	
        	String myAccountEmail = "devpreetestes@gmail.com";
        	String password = "dirjdcospowfrcfa";
        	
        	Session session = Session.getInstance(properties, new Authenticator() {
        		@Override
        		protected PasswordAuthentication getPasswordAuthentication() {
        			return new PasswordAuthentication(myAccountEmail, password);
        		};
        	});
        	
        	MimeMessage message = new MimeMessage(session);
        	message.setFrom(new InternetAddress(myAccountEmail));
        	message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        	message.setSubject(title);
        	message.setText(description, "UTF-8", "html");
        	
        	Transport.send(message);
        	
        	MessageUtil.sendMessage(success_message, FacesMessage.SEVERITY_INFO);
        	
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("mail_server_error"), FacesMessage.SEVERITY_ERROR);
			System.out.println(" ################# ERROR SENDING EMAIL #################");
		}
    }
}
