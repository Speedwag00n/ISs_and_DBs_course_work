package sharing.dormitory.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sharing.dormitory.db.model.Object;
import sharing.dormitory.service.ObjectsService;
import sharing.dormitory.service.ObjectsServiceImpl;
import sharing.dormitory.service.UserService;

import java.util.List;


@Controller
@AllArgsConstructor
public class ObjectsController {
    private final UserService userService;
    private final ObjectsService objectsService;

    @GetMapping("/objects")
    public String objects(Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        model.addAttribute("id", userId);
        List<Object> objects = objectsService.getUserObject(userId);
        model.addAttribute("objects", objects);
        model.addAttribute("newObject", new Object());
        return "objects";
    }

    @GetMapping("/objects/delete/{id}")
    public String delete(Authentication authentication, Model model, @PathVariable String id) {
        objectsService.deleteObject(Integer.parseInt(id));
        return objects(authentication, model);
    }

    @PostMapping("/objects/create")
    public String createObject(@ModelAttribute Object object, Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        objectsService.createObject(object, userId);
        return objects(authentication, model);
    }
}
