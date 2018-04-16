package pl.edu.pb.wi.bai.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import pl.edu.pb.wi.bai.security.firstStep.UsernameAuthenticationToken;

public class ClearPreAuthAuthorityFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest servletReq = (HttpServletRequest) request;
		String requestPath = servletReq.getServletPath();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(shoudlClearContext(requestPath, auth)) {
			//SecurityContextHolder.getContext()
			//.setAuthentication(new AnonymousAuthenticationToken("anonymous", "anonymous",Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))));
			SecurityContextHolder.clearContext();
		}
		chain.doFilter(request, response);
	}

	private boolean shoudlClearContext(String requestPath, Authentication auth) {
		return !requestPath.equals("/secondLoginStep") && auth instanceof UsernameAuthenticationToken && !isStatic(requestPath);
	}

	private boolean isStatic(String path) {
		if(path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/image") || path.startsWith("/webjars")) {
			return true;
		}
		return false;
	}
}
