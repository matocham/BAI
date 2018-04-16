package pl.edu.pb.wi.bai.security;

import java.util.Calendar;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.edu.pb.wi.bai.PasswordMaskGenerator;
import pl.edu.pb.wi.bai.models.BadUser;
import pl.edu.pb.wi.bai.models.User;
import pl.edu.pb.wi.bai.repositories.BadUserRepository;
import pl.edu.pb.wi.bai.repositories.UserRepository;

@Service
public class OracleUserDetailsService implements UserDetailsService {
	public static final String EMPTY_USERNAME = "(empty)";

	@Autowired
	UserRepository userRepository;

	@Autowired
	BadUserRepository badUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			if (username == "") {
				username = EMPTY_USERNAME;
			}
			BadUser bUser = badUserRepository.findByUsername(username);
			if (bUser == null) {
				return new SecurityPrincipal(createNewBadUser(username));
			}
			return new SecurityPrincipal(bUser);
		}
		return new SecurityPrincipal(user);
	}
	
	private BadUser createNewBadUser(String name) {
		PasswordMaskGenerator generator = new PasswordMaskGenerator(new Random().nextInt(8) + 8, 1);
		Random rand = new Random(System.currentTimeMillis());
		BadUser badUser = new BadUser();
		badUser.setUsername(name);
		badUser.setLastFailedLogin(Calendar.getInstance().getTime());
		badUser.setLoginAttempts(1);
		badUser.setCurrentMask(generator.getMasks()[0]);
		badUser.setMaxLoginAttempts(rand.nextInt(8) + 3); // 3-10
		return badUserRepository.save(badUser);
	}
}
