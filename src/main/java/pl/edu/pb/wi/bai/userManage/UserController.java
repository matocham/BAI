package pl.edu.pb.wi.bai.userManage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.edu.pb.wi.bai.models.User;
import pl.edu.pb.wi.bai.repositories.UserRepository;
import pl.edu.pb.wi.bai.security.SecurityPrincipal;

import java.util.List;
@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @GetMapping(value = "/resetUserAttempts")
    public String resetUserAttempts(Model model){
       List<User> users= userRepository.findBlocedUser();
       model.addAttribute("users",users);
       return "resetUserAttempts";
    }
    @PostMapping(value = "resetUserAttempts")
    String deletePermission(@ModelAttribute(name="resetUser")String userToDeletePermissionString){

        System.err.println(userToDeletePermissionString);
        Long userId=Long.parseLong(userToDeletePermissionString);
        User user=userRepository.findOne(userId);
        user.setLoginAttempts(0);
        userRepository.save(user);
        return "redirect:/resetUserAttempts";
    }
    @GetMapping(value = "/settings")
    public String settings(Model model){
        SecurityPrincipal myUserPrincipal = (SecurityPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(myUserPrincipal.getUsername());
        model.addAttribute("user",user);
        return "settings";
    }
    @PostMapping(value = "/settings")
    public String setMaxAttempts(@ModelAttribute(name="maxLoginAttempts")Integer maxLoginAttempts){
        System.err.println(maxLoginAttempts);
        SecurityPrincipal myUserPrincipal = (SecurityPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=userRepository.findByUsername(myUserPrincipal.getUsername());
        user.setMaxLoginAttempts(maxLoginAttempts);
        userRepository.save(user);
        return "redirect:/settings";
    }
}
