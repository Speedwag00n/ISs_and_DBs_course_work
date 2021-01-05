package sharing.dormitory.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sharing.dormitory.db.model.User;
import sharing.dormitory.service.UserService;

@Controller
@AllArgsConstructor
public class MainController {
    private final UserService userService;

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        if (authentication != null) {
            User user = userService.getUser(authentication.getName());
            model.addAttribute("user", user);
        }
        return "index";
    }

}
