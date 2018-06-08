package pl.edu.pb.wi.bai.security.secondStep;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import pl.edu.pb.wi.bai.security.SecurityPrincipal;
import pl.edu.pb.wi.bai.security.firstStep.UsernameAuthenticationToken;

public class PasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final String PASSWORD_PARAM_PREFIX = "p";
	private static final int MAX_PASSWORD_LENGTH = 16;

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
			String mask = ((SecurityPrincipal)principal).getMask();
			String password = getPasswordFromRequest(request, mask);
			PasswordAuthenticationToken authToken = new PasswordAuthenticationToken(username, password);
			SecurityContextHolder.clearContext();
			return this.getAuthenticationManager().authenticate(authToken);
		}
		throw new BadCredentialsException("User not preauthincated");
	}

	private String getPasswordFromRequest(HttpServletRequest request, String mask) {
		String result = "";
		for (int i = 0; i < mask.length(); i++) {
			String charAt = String.valueOf(mask.charAt(i));
			if(charAt.equals("0")){
				continue;
			}
			String partialPass = request.getParameter(PASSWORD_PARAM_PREFIX + i);
			if (partialPass != null) {
				if(partialPass.length() != 1){
					throw new BadCredentialsException("User not preauthincated");
				}
				result += partialPass;
			}
		}
		return result;
	}
	public Boolean isAuth(String username,String password){
		PasswordAuthenticationToken authToken = new PasswordAuthenticationToken(username, password);
		return this.getAuthenticationManager().authenticate(authToken).isAuthenticated();

	}

}
