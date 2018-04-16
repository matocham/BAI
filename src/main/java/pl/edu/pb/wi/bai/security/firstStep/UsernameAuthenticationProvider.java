package pl.edu.pb.wi.bai.security.firstStep;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UsernameAuthenticationProvider implements AuthenticationProvider {
	private final UserDetailsService userDetailsService;

	public UsernameAuthenticationProvider(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernameAuthenticationToken token = (UsernameAuthenticationToken) authentication;
		UserDetails user = userDetailsService.loadUserByUsername((String) token.getPrincipal());
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_PREAUTH"));
		return new UsernameAuthenticationToken(user, authorities);
	}

	@Override
	public boolean supports(Class<?> cls) {
		return UsernameAuthenticationToken.class.isAssignableFrom(cls);
	}
}
