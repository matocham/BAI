package pl.edu.pb.wi.bai.security.secondStep;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import pl.edu.pb.wi.bai.security.firstStep.UsernameAuthenticationToken;

public class PasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	protected PasswordAuthenticationFilter() {
		super(new AntPathRequestMatcher("/loginSecondStep", "POST"));
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
			userToken.setAuthenticated(false);
			userToken.eraseCredentials();
			return this.getAuthenticationManager().authenticate(authToken);
		}
		return null;
	}

}