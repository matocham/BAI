package pl.edu.pb.wi.bai.message;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.edu.pb.wi.bai.repositories.UserRepository;
import pl.edu.pb.wi.bai.security.MyUserPrincipal;

import javax.validation.Valid;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = {"/index", "/"})
    String index(Model model) {
        model.addAttribute("posts", messageService.getAllMessages());
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
    String editMessage(Model model, @PathVariable Long id) {
        EditMessageDto messageDto = new EditMessageDto();
        DisplayMessageDto displayMessageDto = messageService.getMessageById(id);
        MyUserPrincipal myUserPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (hasPermission(displayMessageDto, myUserPrincipal)) {
            messageDto.setText(displayMessageDto.getText());
            messageDto.setId(displayMessageDto.getId());
            model.addAttribute("message", messageDto);
            return "editMessage";
        }
        return "redirect:/index";
    }

    @PostMapping(value = "/edit")
    String processEditMessage(@ModelAttribute("message") @Valid EditMessageDto editMessageDto, BindingResult result) {
        DisplayMessageDto displayMessageDto = messageService.getMessageById(editMessageDto.getId());
        MyUserPrincipal myUserPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (result.hasErrors()) {
            return "editMessage";
        }
        if (hasPermission(displayMessageDto, myUserPrincipal)) {
            messageService.editMessage(editMessageDto);
        }
        return "redirect:/index";
    }
    @GetMapping(value="/delete/{id}")
    String deleteMessage(@PathVariable Long id){
        messageService.deleteMessage(id);
        return "redirect:/index";
    }
    private boolean hasPermission(DisplayMessageDto displayMessageDto, MyUserPrincipal myUserPrincipal) {

        return myUserPrincipal.getUsername().equals(displayMessageDto.getOwner().getUsername()) || displayMessageDto.getAllowedUsers().contains(myUserPrincipal.getUsername());
    }
}
