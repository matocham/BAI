package pl.edu.pb.wi.bai.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {
    @RequestMapping ("/index")
    String index(Model model) {
        return "index";
    }
    @RequestMapping ("/login")
    String login(Model model) {
        return "login";
    }
}
