package pl.edu.pb.wi.bai.security.secondStep;

import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class PasswordAuthenticationToken extends AbstractAuthenticationToken{
	private Object principal;
	private Object cridentials;
	
	
	public PasswordAuthenticationToken(String username, String password) {
		super(null);
		this.principal = username;
		this.cridentials = password;
		super.setAuthenticated(false);
	}
	
	public PasswordAuthenticationToken(Object principal, Object cridentials, List<GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.cridentials = cridentials;
		super.setAuthenticated(true);
	}


	@Override
	public Object getCredentials() {
		return this.cridentials;
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

}
