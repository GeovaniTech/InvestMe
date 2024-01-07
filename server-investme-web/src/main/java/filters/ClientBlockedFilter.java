package filters;

import java.io.IOException;

import jakarta.ejb.EJB;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import keep.security.IKeepSecuritySBean;
import to.client.TOClient;

public class ClientBlockedFilter extends HttpFilter implements Filter {
	
    private static final long serialVersionUID = 7364452106144166728L;

    @EJB
    private IKeepSecuritySBean securitySBean;
    
	public ClientBlockedFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		HttpSession session = request.getSession();

		TOClient client = (TOClient) session.getAttribute("client");
		
		if(client != null && this.getSecuritySBean().isClientBlocked(client.getEmail())) {
			session.invalidate();
			response.setStatus(401);
			response.sendRedirect("/lecoffee/login/userblocked");
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	// Getters and Setters
	public IKeepSecuritySBean getSecuritySBean() {
		return securitySBean;
	}

	public void setSecuritySBean(IKeepSecuritySBean securitySBean) {
		this.securitySBean = securitySBean;
	}

	
}
