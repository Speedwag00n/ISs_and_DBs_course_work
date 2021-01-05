package sharing.dormitory.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sharing.dormitory.dto.UserDTO;
import sharing.dormitory.service.DormitoryService;
import sharing.dormitory.service.UserService;

@AllArgsConstructor
@Controller
@RequestMapping("/")
public class AuthController {

    private final DormitoryService dormitoryService;
    private final UserService userService;

    @GetMapping("register")
    public String openRegisterPage(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("dormitories", dormitoryService.getDormitories());
        return "register";
    }

    @PostMapping("register")
    public String registerUser(@ModelAttribute UserDTO user, Model model) {
        userService.addUser(user);
        return "index";
    }

    @GetMapping("login")
    public String openLoginPage(Authentication authentication, Model model) {
        return "login";
    }

}