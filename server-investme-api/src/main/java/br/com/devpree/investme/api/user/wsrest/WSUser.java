package br.com.devpree.investme.api.user.wsrest;

import java.io.Serializable;

import br.com.devpree.investme.api.user.model.TOUserRequest;
import br.com.devpree.investme.api.user.model.TOUserRestModel;
import br.com.devpree.investme.api.user.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/investme/user")
public class WSUser implements Serializable {

	private static final long serialVersionUID = 2966402697513726982L;
	
	@Inject
	UserService userService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"User"}) 
	@Path("/auth")
	public TOUserRestModel auth(TOUserRequest userRequest) {
		return userService.auth(userRequest.getEmail(), userRequest.getPassword());
	}
}
