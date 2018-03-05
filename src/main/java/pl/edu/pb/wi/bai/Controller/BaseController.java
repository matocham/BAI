package pl.edu.pb.wi.bai.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {
    @RequestMapping ("/")
    String index(Model model) {
        return "index";
    }
}
