package pl.edu.pb.wi.bai.security.secondStep;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import pl.edu.pb.wi.bai.security.firstStep.UsernameAuthenticationToken;

public class PasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public PasswordAuthenticationFilter() {
		super(new AntPathRequestMatcher("/secondLoginStep", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof UsernameAuthenticationToken && auth.isAuthenticated()) {
			UsernameAuthenticationToken userToken = (UsernameAuthenticationToken) auth;
			UserDetails principal = (UserDetails) userToken.getPrincipal();
			String username = principal.getUsername();
			String password = request.getParameter("password");
			PasswordAuthenticationToken authToken = new PasswordAuthenticationToken(username, password);
			SecurityContextHolder.clearContext();
			return this.getAuthenticationManager().authenticate(authToken);
		}
		return null;
	}
}
