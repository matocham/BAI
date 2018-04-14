package pl.edu.pb.wi.bai;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import pl.edu.pb.wi.bai.models.AllowedMessage;
import pl.edu.pb.wi.bai.models.AllowedMessagesPK;
import pl.edu.pb.wi.bai.models.Message;
import pl.edu.pb.wi.bai.models.User;
import pl.edu.pb.wi.bai.register.RegisterService;
import pl.edu.pb.wi.bai.register.UserExistsException;
import pl.edu.pb.wi.bai.repositories.AllowedMessageRepository;
import pl.edu.pb.wi.bai.repositories.BadUserRepository;
import pl.edu.pb.wi.bai.repositories.MessageRepository;
import pl.edu.pb.wi.bai.repositories.PasswordRepository;
import pl.edu.pb.wi.bai.repositories.UserRepository;

@Component
public class StartupDatabaseInitialization implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	RegisterService registerService;

	@Autowired
	UserRepository userRepository;
	@Autowired
	MessageRepository messageRepository;
	@Autowired
	AllowedMessageRepository allowedMessageRepository;
	@Autowired
	PasswordRepository passwordRepository;
	@Autowired
	BadUserRepository badUserRepository;

	@Value("${shouldLoadTestData}")
	boolean shouldLoad;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			if (shouldLoad) {
				loadTestData();
			}
		} catch (UserExistsException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	private void loadTestData() throws UserExistsException {
		for (User user : userRepository.findAll()) {
			user.setCurrentPassword(null);
			userRepository.save(user);
		}
		badUserRepository.deleteAll();
		allowedMessageRepository.deleteAll();
		messageRepository.deleteAll();
		passwordRepository.deleteAll();
		userRepository.deleteAll();
		registerService.register("user1", "password1");
		registerService.register("user2", "password2");
		registerService.register("user3", "password3");
		registerService.register("user4", "password4");
		User user1 = userRepository.findByUsername("user1");
		User user2 = userRepository.findByUsername("user2");
		User user3 = userRepository.findByUsername("user3");
		User user4 = userRepository.findByUsername("user4");

		Message msg = new Message();
		msg.setModerator(user1);
		msg.setText("Message1 Message1 Message1 Message1 Message1 Message1 Message1 Message1");
		msg = messageRepository.save(msg);
		addPermission(user2, msg);
		addPermission(user3, msg);

		msg = new Message();
		msg.setModerator(user2);
		msg.setText("Message2 Message2 Message2 Message2 Message2 Message2 Message2 Message2");
		msg = messageRepository.save(msg);
		addPermission(user1, msg);
		addPermission(user3, msg);

		msg = new Message();
		msg.setModerator(user3);
		msg.setText("Message3 Message3 Message3 Message3 Message3 Message3 Message3 Message3");
		msg = messageRepository.save(msg);
		addPermission(user4, msg);

		msg = new Message();
		msg.setModerator(user4);
		msg.setText("Message4 Message4 Message4 Message4 Message4 Message4 Message4 Message4");
		msg = messageRepository.save(msg);
	}

	private void addPermission(User user2, Message msg) {
		AllowedMessagesPK allowedMessagesPK = new AllowedMessagesPK();
		allowedMessagesPK.setMessageId(msg);
		allowedMessagesPK.setUserId(user2);
		AllowedMessage allowedMessage = new AllowedMessage();
		allowedMessage.setAllowedId(allowedMessagesPK);
		allowedMessageRepository.save(allowedMessage);
	}
}
