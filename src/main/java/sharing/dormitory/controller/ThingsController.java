package sharing.dormitory.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sharing.dormitory.db.model.Object;
import sharing.dormitory.db.model.Service;
import sharing.dormitory.service.ThingsService;
import sharing.dormitory.service.UserService;

import javax.validation.Valid;
import java.util.List;


@Controller
@AllArgsConstructor
public class ThingsController {
    private final UserService userService;
    private final ThingsService thingsService;

    @GetMapping("/things")
    public String things(Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        model.addAttribute("id", userId);
        List<Object> objects = thingsService.getUserObject(userId);
        model.addAttribute("objects", objects);
        List<Service> services = thingsService.getUserService(userId);
        model.addAttribute("services", services);
        model.addAttribute("newObject", new Object());
        model.addAttribute("newService", new Service());
        return "things";
    }

    @DeleteMapping("/things/{id}")
    public String delete(Authentication authentication, Model model) {
        return things(authentication, model);
    }

    @PostMapping("/things/object/create")
    public String createObject(@ModelAttribute Object object, Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        thingsService.createObject(object, userId);
        return things(authentication, model);
    }
}
