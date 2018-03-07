package pl.edu.pb.wi.bai.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    String processRegister(@ModelAttribute("user") RegisterDto filledUser){
        registerService.register(filledUser.getUsername(), filledUser.getPassword());
        return "index";
    }
    @GetMapping("/login")
    String login(){
        return "login";
    }

}
