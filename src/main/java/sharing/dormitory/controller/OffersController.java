package sharing.dormitory.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sharing.dormitory.db.model.Offer;
import sharing.dormitory.service.OffersServiceImpl;
import sharing.dormitory.service.UserService;

import java.util.List;


@Controller
@AllArgsConstructor
public class OffersController {
    private final UserService userService;
    private final OffersServiceImpl offersService;

    @GetMapping("/offers")
    public String offers(Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        model.addAttribute("id", userId);
        List<Offer> offers = offersService.getOffers();
        model.addAttribute("offers", offers);
        model.addAttribute("newOffer", new Offer());
        return "offers";
    }

    @DeleteMapping("/offers/{id}")
    public String delete(Authentication authentication, Model model) {
        return offers(authentication, model);
    }

    @PostMapping("/offers/create")
    public String createOffers(@ModelAttribute Offer offer, Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        offersService.createOffer(offer, userId);
        return offers(authentication, model);
    }
}
