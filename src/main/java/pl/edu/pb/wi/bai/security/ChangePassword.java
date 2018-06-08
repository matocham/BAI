package pl.edu.pb.wi.bai.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.edu.pb.wi.bai.models.Password;
import pl.edu.pb.wi.bai.models.User;
import pl.edu.pb.wi.bai.register.RegisterDto;
import pl.edu.pb.wi.bai.register.RegisterService;
import pl.edu.pb.wi.bai.repositories.PasswordRepository;
import pl.edu.pb.wi.bai.repositories.UserRepository;
import pl.edu.pb.wi.bai.security.secondStep.PasswordAuthenticationFilter;
import pl.edu.pb.wi.bai.security.secondStep.PasswordAuthenticationProvider;
import pl.edu.pb.wi.bai.security.secondStep.PasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
public class ChangePassword {
    private static final String PASSWORD_PARAM_PREFIX = "p";
    private static final int MAX_PASSWORD_LENGTH = 16;
    @Autowired
    RegisterService registerService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PasswordRepository passwordRepository;

    @GetMapping("/changePassword")
    String ChangePassword(Model model) {
        SecurityPrincipal myUserPrincipal = (SecurityPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(myUserPrincipal.getUsername());

        List<String> letters = Arrays.asList(user.getCurrentPassword().getMask().split(""));
        model.addAttribute("mask", letters);

        RegisterDto emptyDto = new RegisterDto();
        model.addAttribute("user", emptyDto);
        return "changePasswordSecond";
    }

    @PostMapping("/changePassword")
    String changePasswordFirstStepValidate(HttpServletRequest request, Model model, @ModelAttribute("user") @Valid RegisterDto filledUser, BindingResult result) {
        SecurityPrincipal myUserPrincipal = (SecurityPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser=userRepository.findByUsername(myUserPrincipal.getUsername());
        if(filledUser.getPassword().equals(filledUser.getPasswordRepeat())) {
            if (passwordEncoder.matches(getPasswordFromRequest(request, currentUser.getCurrentPassword().getMask()), currentUser.getCurrentPassword().getPassword())) {
                registerService.changePassword(filledUser.getPassword());
                System.err.println("zmieniono");
            }
        }
        else
        {
            System.err.println("hasła nie są takie same");
        }

        return "redirect:/settings";
    }

    private String getPasswordFromRequest(HttpServletRequest request, String mask) {
        String result = "";
        for (int i = 0; i < mask.length(); i++) {
            String charAt = String.valueOf(mask.charAt(i));
            if(charAt.equals("0")){
                continue;
            }
            String partialPass = request.getParameter(PASSWORD_PARAM_PREFIX + i);
            if (partialPass != null) {
                result += partialPass.charAt(0);
            }
        }
        return result;
    }
}
