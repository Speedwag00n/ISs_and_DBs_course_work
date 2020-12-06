package sharing.dormitory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sharing.dormitory.db.repository.DormitoryRepository;
import sharing.dormitory.dto.UserDTO;
import sharing.dormitory.service.DormitoryService;
import sharing.dormitory.service.UserService;

@Controller
@RequestMapping("/")
public class AuthController {

    private DormitoryService dormitoryService;
    private UserService userService;

    @Autowired
    public AuthController(DormitoryService dormitoryService, UserService userService) {
        this.dormitoryService = dormitoryService;
        this.userService = userService;
    }

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
    public String openLoginPage(Model model) {
        return "login";
    }

}