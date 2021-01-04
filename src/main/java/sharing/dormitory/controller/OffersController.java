package sharing.dormitory.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sharing.dormitory.db.model.Offer;
import sharing.dormitory.service.OffersService;
import sharing.dormitory.service.UserService;

import java.util.List;


@Controller
@AllArgsConstructor
public class OffersController {
    private final UserService userService;
    private final OffersService offersService;

    @GetMapping("/alloffers")
    public String allOffers(Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        List<Offer> offers = offersService.getOffers(userId);
        model.addAttribute("offers", offers);
        model.addAttribute("newOffer", new Offer());
        return "alloffers";
    }

    @GetMapping("/offers")
    public String offers(Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        List<Offer> offers = offersService.getUserOffers(userId);
        model.addAttribute("offers", offers);
        model.addAttribute("newOffer", new Offer());
        return "offers";
    }

    @GetMapping("/offers/delete/{id}")
    public String delete(Authentication authentication, Model model, @PathVariable Integer id) {
        offersService.deleteOffer(id);
        return offers(authentication, model);
    }

    @PostMapping("/offers/create")
    public String createOffers(@ModelAttribute Offer offer, Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        offersService.createOffer(offer, userId);
        return offers(authentication, model);
    }
}
