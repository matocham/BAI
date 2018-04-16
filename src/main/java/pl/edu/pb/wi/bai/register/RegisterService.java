package pl.edu.pb.wi.bai.register;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.edu.pb.wi.bai.PasswordMaskGenerator;
import pl.edu.pb.wi.bai.models.Password;
import pl.edu.pb.wi.bai.models.User;
import pl.edu.pb.wi.bai.repositories.PasswordRepository;
import pl.edu.pb.wi.bai.repositories.UserRepository;

@Service
public class RegisterService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final PasswordRepository passwordRepository;

	public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			PasswordRepository passwordRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.passwordRepository = passwordRepository;
	}

	@Transactional
	public User register(String username, String password) throws UserExistsException {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			throw new UserExistsException();
		}

		user = new User();
		user.setUsername(username);
		user.setLoginAttempts(0);
		user.setMaxLoginAttempts(10);
		user = userRepository.save(user);
		addPasswords(password, user);
		return user;
	}

	private void addPasswords(String password, User user) {
		PasswordMaskGenerator generator = new PasswordMaskGenerator(password.length(), 10);
		String[] masks = generator.getMasks();
		List<Password> passwords = new ArrayList<>();

		for (String mask : masks) {
			String partialPassword = "";
			for (int i = 0; i < password.length(); i++) {
				if (mask.charAt(i) == '1') {
					partialPassword += password.charAt(i);
				}
			}
			Password p = new Password();
			p.setMask(mask);
			p.setPassword(passwordEncoder.encode(partialPassword));
			p.setUser(user);
			passwords.add(passwordRepository.save(p));
		}
		Password currentPass = passwords.get(new Random(System.currentTimeMillis()).nextInt(passwords.size()));
		user.setCurrentPassword(currentPass);
		userRepository.save(user);
	}
}
