package sharing.dormitory.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sharing.dormitory.db.model.Object;
import sharing.dormitory.db.model.Service;
import sharing.dormitory.service.ThingsService;

import javax.validation.Valid;
import java.util.List;


@Controller
@AllArgsConstructor
public class ThingsController {
    private final ThingsService thingsService;

    @GetMapping("/things")
    public String things(@Valid @RequestParam(name="id") Integer id, Model model) {
        model.addAttribute("id", id);
        List<Object> objects = thingsService.getUserObject(id);
        model.addAttribute("objects", objects);
        List<Service> services = thingsService.getUserService(id);
        model.addAttribute("services", services);
        model.addAttribute("newObject", new Object());
        model.addAttribute("newService", new Service());
        return "things";
    }

    @DeleteMapping("/things/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        return things(id, model);
    }

    @PostMapping("/things/object/create")
    public String createObject(@ModelAttribute Object object, Model model) {
        thingsService.createObject(object, 1);
        return "things";
    }
}
