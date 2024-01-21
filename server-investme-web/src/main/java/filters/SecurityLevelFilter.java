package filters;

import java.io.IOException;

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
import to.client.TOClient;

/**
 * Servlet Filter implementation class SecurityLevelFilter
 */
public class SecurityLevelFilter extends HttpFilter implements Filter {
    
	private static final long serialVersionUID = -1230273312344471544L;
	
    public SecurityLevelFilter() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		HttpSession session = request.getSession(true);
		
		TOClient client = (TOClient) session.getAttribute("client");
		
		if(client != null) {
			chain.doFilter(request, response);
		} else {
			response.setStatus(401);
			response.sendRedirect("/investme/login");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
