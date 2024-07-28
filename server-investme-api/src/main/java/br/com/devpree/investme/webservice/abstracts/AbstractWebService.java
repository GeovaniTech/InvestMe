package br.com.devpree.investme.webservice.abstracts;

import java.io.Serializable;

import com.google.gson.Gson;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public abstract class AbstractWebService implements Serializable {

	private static final long serialVersionUID = 1L;

	protected static Gson gson;
	
	protected Gson getGson() {
		if (gson == null) {
			return new Gson();
		}
		
		return gson;
	}
}
