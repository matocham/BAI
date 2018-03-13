package pl.edu.pb.wi.bai.message;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    @RequestMapping (value={"/index", "/"})
    String index(Model model) {
        model.addAttribute("posts", messageService.getAllMessages());
        return "index";
    }
}
