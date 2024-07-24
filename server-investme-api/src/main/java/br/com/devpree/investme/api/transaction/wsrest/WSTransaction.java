package br.com.devpree.investme.api.transaction.wsrest;

import java.io.Serializable;
import java.util.List;

import br.com.devpree.investme.api.common.transferobject.TOGenericRequest;
import br.com.devpree.investme.api.transaction.service.TransactionService;
import br.com.devpree.investme.api.transaction.transferobject.TOTransactionRestModel;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/investme/transaction")
public class WSTransaction implements Serializable {
	
	private static final long serialVersionUID = -5356669334984339402L;
	
	@Inject
	TransactionService transactionService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"User"}) 
	@Path("/list")
	public List<TOTransactionRestModel> list(TOGenericRequest genericRequest) { 
		return transactionService.list(genericRequest.getEmail());
	}
}
