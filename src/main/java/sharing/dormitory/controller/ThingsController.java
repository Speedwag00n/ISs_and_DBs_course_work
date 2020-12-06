package sharing.dormitory.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import sharing.dormitory.db.model.Object;
import sharing.dormitory.db.model.Service;
import sharing.dormitory.service.ThingService;

import javax.validation.Valid;
import java.util.List;


@Controller
@AllArgsConstructor
public class ThingsController {
    private final ThingService thingService;

    @GetMapping("/things")
    public String things(@Valid @RequestParam(name="id") Integer id, Model model) {
        List<Object> objects = thingService.getUserObject(id);
        List<Service> services = thingService.getUserService(id);
        model.addAttribute("objects", objects);
        model.addAttribute("services", services);
        return "things";
    }

    @DeleteMapping("/things/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        return things(id, model);
    }

}
