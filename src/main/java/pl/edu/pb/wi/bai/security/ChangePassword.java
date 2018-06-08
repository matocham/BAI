package pl.edu.pb.wi.bai.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    PasswordAuthenticationFilter passwordAuthenticationFilter;

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
        String password = getPasswordFromRequest(request);
        SecurityPrincipal myUserPrincipal = (SecurityPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        passwordAuthenticationFilter.isAuth(myUserPrincipal.getUsername(), getPasswordFromRequest(request));
        registerService.changePassword(filledUser.getPassword());
        return "redirect:/settings";
    }

    private String getPasswordFromRequest(HttpServletRequest request) {
        String result = "";
        for (int i = 0; i < MAX_PASSWORD_LENGTH; i++) {
            String partialPass = request.getParameter(PASSWORD_PARAM_PREFIX + i);
            if (partialPass != null) {
                result += partialPass;
            }
        }
        return result;
    }
}
