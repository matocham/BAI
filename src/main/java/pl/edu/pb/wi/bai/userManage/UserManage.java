package pl.edu.pb.wi.bai.userManage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.edu.pb.wi.bai.models.User;
import pl.edu.pb.wi.bai.repositories.UserRepository;

import java.util.List;
@Controller
public class UserManage {
    @Autowired
    UserRepository userRepository;
    @GetMapping(value = "/resetUserAttempts")
    public String resetUserAttempts(Model model){
       List<User> users= userRepository.findBlocedUser();
       model.addAttribute("users",users);
       return "resetUserAttempts";
    }
}
