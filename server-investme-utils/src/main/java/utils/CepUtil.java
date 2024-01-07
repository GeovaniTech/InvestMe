package utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import jakarta.faces.application.FacesMessage;

public class CepUtil {
	public static String getCepJSON(String cep) {
		try {
			String url = "https://viacep.com.br/ws/" + cep + "/json/";
			
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
			
		    HttpResponse<String> response = client
				     .send(request, BodyHandlers.ofString()); 
			
			return response.body();
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.sendMessage(MessageUtil.getMessageFromProperties("error_get_cep_informations"), FacesMessage.SEVERITY_ERROR);
		}
		return null;
	}
}
