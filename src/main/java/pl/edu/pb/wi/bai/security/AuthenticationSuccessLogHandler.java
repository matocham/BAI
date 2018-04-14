package pl.edu.pb.wi.bai.security;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import pl.edu.pb.wi.bai.models.Password;
import pl.edu.pb.wi.bai.models.User;
import pl.edu.pb.wi.bai.repositories.PasswordRepository;
import pl.edu.pb.wi.bai.repositories.UserRepository;

@Component
public class AuthenticationSuccessLogHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private final UserRepository userRepository;
	private final PasswordRepository passwordRepository;

	public AuthenticationSuccessLogHandler(UserRepository userRepository, PasswordRepository passwordRepository) {
		super();
		this.userRepository = userRepository;
		this.passwordRepository = passwordRepository;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		super.onAuthenticationSuccess(request, response, authentication);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String username = ((UserDetails) auth.getPrincipal()).getUsername();
		User user = userRepository.findByUsername(username);
		Date lastSuccessLogin = user.getLastLoginDate();
		user.setLastLoginDate(new Date());
		user.setLoginAttempts(0);
		user.setLastFailedLogin(null);

		List<Password> partialPasswords = passwordRepository.findByUser(user);
		partialPasswords.remove(user.getCurrentPassword());
		Password nextPartialPassword = partialPasswords.get(new Random().nextInt(partialPasswords.size()));
		user.setCurrentPassword(nextPartialPassword);
		userRepository.save(user);

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute("loginDate", lastSuccessLogin);
		}

	}
}
