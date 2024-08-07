package br.com.devpree.investme.api.payments.wsrest;

import java.io.Serializable;
import java.util.List;

import br.com.devpree.investme.api.common.transferobject.TOGenericRequest;
import br.com.devpree.investme.api.payments.service.PaymentService;
import br.com.devpree.investme.api.payments.transferobject.TOPaymentRestModel;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/investme/payment")
public class WSPayment implements Serializable {

	private static final long serialVersionUID = 5775223766438782927L;
	
	@Inject
	PaymentService paymentService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"User"}) 
	@Path("/list")
	public List<TOPaymentRestModel> list(TOGenericRequest genericRequest) {
		return paymentService.list(genericRequest.getEmail());
	}
}
