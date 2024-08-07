package br.com.devpree.investme.api.category.wsrest;

import java.io.Serializable;
import java.util.List;

import br.com.devpree.investme.api.category.service.CategoryService;
import br.com.devpree.investme.api.category.transferobject.TOCategoryRestModel;
import br.com.devpree.investme.api.common.transferobject.TOGenericRequest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/investme/category")
public class WSCategory implements Serializable {

	private static final long serialVersionUID = 5775223766438782927L;
	
	@Inject
	CategoryService categoryService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"User"}) 
	@Path("/list")
	public List<TOCategoryRestModel> list(TOGenericRequest genericRequest) {
		return categoryService.list(genericRequest.getEmail());
	}
}
