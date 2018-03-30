package pl.edu.pb.wi.bai.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
				throw new UsernameNotFoundException(username);
			}
			return new SecurityPrincipal(bUser);
		}
		return new SecurityPrincipal(user);
	}
}
