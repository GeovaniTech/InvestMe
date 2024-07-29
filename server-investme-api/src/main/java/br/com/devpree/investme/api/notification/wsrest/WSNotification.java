package br.com.devpree.investme.api.notification.wsrest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.devpree.investme.api.common.transferobject.TOGenericRequest;
import br.com.devpree.investme.api.notification.service.NotificationService;
import br.com.devpree.investme.api.notification.transferobject.TONotificationRestModel;
import br.com.devpree.investme.webservice.brapi.BrapiWebService;
import br.com.devpree.investme.webservice.brapi.transferobject.TOTickerBrapiAPI;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("investme/notification")
public class WSNotification implements Serializable {

	private static final long serialVersionUID = 1444840031152976328L;

	@Inject
	NotificationService notificationService;
	
	@Inject
	BrapiWebService brapiWebservice;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"User"}) 
	@Path("/investments")
	public List<TONotificationRestModel> notifyInvestments(TOGenericRequest genericRequest){ 
		List<TONotificationRestModel> transactions = notificationService.getTransactionInvestments(genericRequest.getEmail());
		
		List<TONotificationRestModel> readyNotifications = new ArrayList<>();
		
		for(TONotificationRestModel transaction : transactions) {
			try {				
				TOTickerBrapiAPI ticker = brapiWebservice.getTicker(transaction.getActive());

				if (ticker == null || ticker.getCurrentPrice() == null) {
					continue;
				}
				
				transaction.setActualPrice(ticker.getCurrentPrice());
				
				if(transaction.getActualPrice() < transaction.getAvaragePrice()) {
					readyNotifications.add(transaction);
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		return readyNotifications;
	}
}
