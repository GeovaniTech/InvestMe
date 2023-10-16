package errors;

import jakarta.faces.application.NavigationHandler;
import jakarta.faces.application.ViewExpiredException;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ExceptionQueuedEvent;
import jakarta.faces.event.ExceptionQueuedEventContext;

import java.util.Iterator;
import java.util.Map;

import jakarta.faces.FacesException;
 
public class ViewExpiredExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;
    
    @SuppressWarnings("deprecation")
	public ViewExpiredExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void handle() throws FacesException {
        for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable t = context.getException();
            if (t instanceof ViewExpiredException) {
                ViewExpiredException vee = (ViewExpiredException) t;
                FacesContext fc = FacesContext.getCurrentInstance();
                Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
                NavigationHandler nav = fc.getApplication().getNavigationHandler();
                try {
                    requestMap.put("jakarta.servlet.error.exception_name", vee.getClass().getName());
                    requestMap.put("jakarta.servlet.error.message", vee.getMessage());

                    nav.handleNavigation(fc, null, "/login.xhtml?faces-redirect=true");
                    fc.renderResponse(); 
                } finally {
                    i.remove();
                }
            }
        }
        getWrapped().handle();
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }
}