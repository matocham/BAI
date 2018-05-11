package pl.edu.pb.wi.bai.message;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.edu.pb.wi.bai.models.User;
import pl.edu.pb.wi.bai.repositories.UserRepository;
import pl.edu.pb.wi.bai.security.SecurityPrincipal;

@Controller
public class MessageController {

	private static final String LOGIN_DATE = "loginDate";
	@Autowired
	MessageService messageService;
	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = { "/index", "/" })
	String index(Model model, HttpSession session) {
		model.addAttribute("posts", messageService.getAllMessages());
		Object lastLoginDate = session.getAttribute(LOGIN_DATE);
		if (lastLoginDate != null) {
			model.addAttribute(LOGIN_DATE, lastLoginDate);
			session.removeAttribute(LOGIN_DATE);
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth);
		return "index";
	}

	@GetMapping(value = "/addMessage")
	String addMessage(Model model) {
		MessageDto messageDto = new MessageDto();
		model.addAttribute("message", messageDto);
		return "addMessage";

	}

	@PostMapping(value = "/addMessage")
	String addMessage(@ModelAttribute("message") @Valid MessageDto messageDto, BindingResult result) {
		if (result.hasErrors()) {
			return "addMessage";
		}
		messageService.newMessage(messageDto.getText());
		return "redirect:/index";
	}

	@GetMapping(value = "/edit/{id}")
	String editMessage(Model model, @PathVariable Long id) throws MessageManageException {
		EditMessageDto messageDto = new EditMessageDto();
		DisplayMessageDto displayMessageDto = messageService.getMessageById(id);
		SecurityPrincipal myUserPrincipal = (SecurityPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (hasPermission(displayMessageDto, myUserPrincipal)) {
			messageDto.setText(displayMessageDto.getText());
			messageDto.setId(displayMessageDto.getId());
			model.addAttribute("message", messageDto);
			return "editMessage";
		} else {
			throw new MessageManageException();
		}
	}

	@PostMapping(value = "/edit")
	String processEditMessage(@ModelAttribute("message") @Valid EditMessageDto editMessageDto, BindingResult result)
			throws MessageManageException {
		DisplayMessageDto displayMessageDto = messageService.getMessageById(editMessageDto.getId());
		SecurityPrincipal myUserPrincipal = (SecurityPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (result.hasErrors()) {
			return "editMessage";
		}
		if (hasPermission(displayMessageDto, myUserPrincipal)) {
			messageService.editMessage(editMessageDto);
		}
		return "redirect:/index";
	}

	@GetMapping(value = "/delete/{id}")
	String deleteMessage(@PathVariable Long id) throws MessageManageException {
		messageService.deleteMessage(id);
		return "redirect:/index";
	}

	private boolean hasPermission(DisplayMessageDto displayMessageDto, SecurityPrincipal myUserPrincipal) {

		return myUserPrincipal.getUsername().equals(displayMessageDto.getOwner().getUsername())
				|| displayMessageDto.getAllowedUsers().contains(myUserPrincipal.getUsername());
	}

	@GetMapping(value = "manage/{id}")
	String managePermissions(Model model, @PathVariable Long id) throws MessageManageException {
		DisplayMessageDto messageDto = messageService.getMessageById(id);
		SecurityPrincipal myUserPrincipal = (SecurityPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (!myUserPrincipal.getUsername().equals(messageDto.getOwner().getUsername())) {
			throw new MessageManageException();
		}
		List<User> users = userRepository.findAll().stream()
				.filter(u -> !messageDto.getAllowedUsers().contains(u.getUsername()))
				.filter(u -> !myUserPrincipal.getUsername().equals(u.getUsername())).collect(Collectors.toList());
		model.addAttribute("remainingUsers", users);
		model.addAttribute("message", messageDto);
		model.addAttribute("allowedUsers", messageDto.getAllowedUsers().stream()
				.map(u -> userRepository.findByUsername(u)).collect(Collectors.toList()));
		model.addAttribute("id", id);
		return "manage";
	}

	@PostMapping(value = "manage")
	String newPermission(@ModelAttribute(name = "newPermission") Long userToAddPermission,
			@ModelAttribute(name = "id") Long messageId) throws MessageManageException {
		messageService.addPermission(messageId, userToAddPermission);
		return "redirect:/manage/" + messageId;
	}

	@PostMapping(value = "delete")
	String deletePermission(@ModelAttribute(name = "deletePermission") Long userToDeletePermission,
			@ModelAttribute(name = "id") Long messageId) throws MessageManageException {
		messageService.deletePermission(messageId, userToDeletePermission);
		return "redirect:/manage/" + messageId;
	}

	@ExceptionHandler({ MessageManageException.class, NullPointerException.class, NumberFormatException.class })
	void handleBadRequests(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value());
	}
}
