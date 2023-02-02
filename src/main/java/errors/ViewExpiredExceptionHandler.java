package errors;

import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
 
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
                    requestMap.put("javax.servlet.error.exception_name", vee.getClass().getName());
                    requestMap.put("javax.servlet.error.message", vee.getMessage());

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