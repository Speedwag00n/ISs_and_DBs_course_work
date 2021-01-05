package sharing.dormitory.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sharing.dormitory.db.model.Service;
import sharing.dormitory.service.ServicesService;
import sharing.dormitory.service.UserService;

import java.util.List;


@Controller
@AllArgsConstructor
public class ServicesController {
    private final UserService userService;
    private final ServicesService servicesService;

    @GetMapping("/services")
    public String services(Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        List<Service> services = servicesService.getUserService(userId);
        model.addAttribute("services", services);
        model.addAttribute("newService", new Service());
        return "services";
    }

    @PostMapping("/services/delete/{id}")
    public String delete(Authentication authentication, Model model, @PathVariable Integer id) {
        servicesService.deleteService(id);
        return services(authentication, model);
    }
    @PostMapping("/services/create")
    public String createService(@ModelAttribute Service service, Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        servicesService.createService(service, userId);
        return services(authentication, model);
    }
}
