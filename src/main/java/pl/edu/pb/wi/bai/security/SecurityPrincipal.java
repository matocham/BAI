package pl.edu.pb.wi.bai.security;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import pl.edu.pb.wi.bai.models.BadUser;
import pl.edu.pb.wi.bai.models.User;

public class SecurityPrincipal implements UserDetails {
	public static final int delayMulitplier = 100;
	private String password;
	private String username;
	private boolean isAccountBlocked = false;
	private boolean isExpired = false;

	public SecurityPrincipal(User user) {
		this.password = user.getPassword();
		this.username = user.getUsername();
		isAccountBlocked = user.getLoginAttempts() >= user.getMaxLoginAttempts();
		Date lastFailedLogin = user.getLastFailedLogin();
		if (lastFailedLogin != null) {
			Date currentTime = Calendar.getInstance().getTime();
			long timeDifference = (currentTime.getTime() - lastFailedLogin.getTime())/1000;
			isExpired = timeDifference < user.getLoginAttempts()* delayMulitplier;
		}
	}

	public SecurityPrincipal(BadUser bUser) {
		this.password = bUser.getPassword();
		this.username = bUser.getUsername();
		isAccountBlocked = bUser.getLoginAttempts() >= bUser.getMaxLoginAttempts();
		Date lastFailedLogin = bUser.getLastFailedLogin();
		if (lastFailedLogin != null) {
			Date currentTime = Calendar.getInstance().getTime();
			long timeDifference = (currentTime.getTime() - lastFailedLogin.getTime())/1000;
			isExpired = timeDifference < bUser.getLoginAttempts()*delayMulitplier;
		}
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !isExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !isAccountBlocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
