package pl.edu.pb.wi.bai.register;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    String loginSecondStep(){
        return "loginSecondStep";
    }

}
