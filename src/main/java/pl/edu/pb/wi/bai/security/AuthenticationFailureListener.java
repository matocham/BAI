package pl.edu.pb.wi.bai.security;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import pl.edu.pb.wi.bai.models.BadUser;
import pl.edu.pb.wi.bai.models.User;
import pl.edu.pb.wi.bai.repositories.BadUserRepository;
import pl.edu.pb.wi.bai.repositories.UserRepository;
import pl.edu.pb.wi.bai.security.secondStep.PasswordAuthenticationToken;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BadUserRepository badUserRepository;

	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
		PasswordAuthenticationToken tk = (PasswordAuthenticationToken) e.getSource();
		String username = tk.getName();
		if (username == "" || username == null) {
			username = OracleUserDetailsService.EMPTY_USERNAME;
		}
		User user = userRepository.findByUsername(username);
		if (user != null) {
			user.setLoginAttempts(user.getLoginAttempts() + 1);
			user.setLastFailedLogin(Calendar.getInstance().getTime());
			userRepository.save(user);
		} else {
			BadUser bUser = badUserRepository.findByUsername(username);
			if (bUser != null) {
				bUser.setLoginAttempts(bUser.getLoginAttempts() + 1);
				bUser.setLastFailedLogin(Calendar.getInstance().getTime());
				badUserRepository.save(bUser);
			}
		}
	}
}
