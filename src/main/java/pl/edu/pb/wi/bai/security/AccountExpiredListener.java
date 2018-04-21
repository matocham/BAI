package pl.edu.pb.wi.bai.security;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureExpiredEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import pl.edu.pb.wi.bai.models.BadUser;
import pl.edu.pb.wi.bai.models.User;
import pl.edu.pb.wi.bai.repositories.BadUserRepository;
import pl.edu.pb.wi.bai.repositories.UserRepository;

@Component
public class AccountExpiredListener implements ApplicationListener<AuthenticationFailureExpiredEvent> {
	private static final String LOGIN_ERROR = "loginError";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BadUserRepository badUserRepository;

	@Override
	public void onApplicationEvent(AuthenticationFailureExpiredEvent e) {
		UsernamePasswordAuthenticationToken tk = (UsernamePasswordAuthenticationToken) e.getSource(); // TODO change token class after merge to master
		Date retryDate = null;
		Date failedLoginDate = null;
		int attempts = 0;
		String username = tk.getName();
		if (username == "" || username == null) {
			username = OracleUserDetailsService.EMPTY_USERNAME;
		}
		User user = userRepository.findByUsername(username);
		if (user != null) {
			attempts = user.getLoginAttempts();
			failedLoginDate = user.getLastFailedLogin();
		} else {
			BadUser bUser = badUserRepository.findByUsername(username);
			if (bUser != null) {
				attempts = bUser.getLoginAttempts();
				failedLoginDate = bUser.getLastFailedLogin();
			}
		}
		if (attempts != 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(failedLoginDate);
			calendar.add(Calendar.SECOND, attempts * SecurityPrincipal.DELY_MULTIPLIER);
			retryDate = calendar.getTime();
			addExpiredMessage(retryDate);
		}
	}

	private void addExpiredMessage(Date retryDate) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		String message = "Twoje konto jest zablokowane. Sprbój ponownie "
				+ (new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(retryDate)) + ".";
		session.setAttribute(LOGIN_ERROR, message);

	}
}
