package pl.edu.pb.wi.bai.register;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pl.edu.pb.wi.bai.security.SecurityPrincipal;

@Controller
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @GetMapping("/register")
    String register(Model model){
        RegisterDto emptyDto = new RegisterDto();
        model.addAttribute("user", emptyDto);
        return "register";
    }

    @PostMapping("/register")
    String processRegister(@ModelAttribute("user") @Valid RegisterDto filledUser, BindingResult result) {
        if (result.hasErrors()) {
            return "/register";
        }
        try {
            registerService.register(filledUser.getUsername(), filledUser.getPassword());
        } catch (UserExistsException e) {
            result.rejectValue("username","100", e.getMessage());
            return "/register";
        }
        return "redirect:/login";
    }
    @GetMapping("/login")
    String login(){
        return "login";
    }
    
    @GetMapping("/secondLoginStep")
    String loginSecondStep(Model model, @AuthenticationPrincipal SecurityPrincipal preauthorized){
    	if(preauthorized != null) {
    		List<String> letters = Arrays.asList(preauthorized.getMask().split(""));
    		model.addAttribute("mask", letters);
    	}
        return "loginSecondStep";
    }

}
