package pl.edu.pb.wi.bai.security.firstStep;

import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsernameAuthenticationToken extends AbstractAuthenticationToken {

	private Object principal;

	public UsernameAuthenticationToken(String username) {
		super(null);
		this.principal = username;
		super.setAuthenticated(false);
	}

	public UsernameAuthenticationToken(Object principal, List<GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		super.setAuthenticated(true);
	}

	@Override
	public String getName() {
		if (principal instanceof String) {
			return (String) principal;
		} else {
			return ((UserDetails) principal).getUsername();
		}
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}
}
