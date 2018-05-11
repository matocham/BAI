package pl.edu.pb.wi.bai.security;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import pl.edu.pb.wi.bai.models.BadUser;
import pl.edu.pb.wi.bai.models.User;
import pl.edu.pb.wi.bai.repositories.BadUserRepository;
import pl.edu.pb.wi.bai.repositories.UserRepository;
import pl.edu.pb.wi.bai.security.secondStep.PasswordAuthenticationToken;

@Component
public class AccountBlockedListener implements ApplicationListener<AuthenticationFailureLockedEvent> {
	private static final String LOGIN_ERROR = "loginError";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BadUserRepository badUserRepository;

	@Override
	public void onApplicationEvent(AuthenticationFailureLockedEvent e) {
		PasswordAuthenticationToken tk = (PasswordAuthenticationToken) e.getSource();
		String username = tk.getName();
		boolean blocked = false;

		if (username == "" || username == null) {
			username = OracleUserDetailsService.EMPTY_USERNAME;
		}
		User user = userRepository.findByUsername(username);
		if (user != null) {
			blocked = user.getLoginAttempts() >= user.getMaxLoginAttempts();
		} else {
			BadUser bUser = badUserRepository.findByUsername(username);
			if (bUser != null) {
				blocked = bUser.getLoginAttempts() >= bUser.getMaxLoginAttempts();
			}
		}

		if (blocked) {
			addBlockedMessage();
		}
	}

	private void addBlockedMessage() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		String message = "Twoje konto zostao zablokowane. Skontaktuj się z administratorem systemu";
		session.setAttribute(LOGIN_ERROR, message);

	}

}
