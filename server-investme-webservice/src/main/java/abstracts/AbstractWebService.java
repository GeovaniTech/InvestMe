package abstracts;

import com.google.gson.Gson;

public abstract class AbstractWebService {
	protected static Gson gson;
	
	protected Gson getGson() {
		if (gson == null) {
			return new Gson();
		}
		
		return gson;
	}
}
