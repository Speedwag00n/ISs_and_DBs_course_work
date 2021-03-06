package sharing.dormitory.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sharing.dormitory.db.enm.ObjectState;
import sharing.dormitory.db.model.Offer;
import sharing.dormitory.db.model.Request;
import sharing.dormitory.dto.OfferRequestDTO;
import sharing.dormitory.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@AllArgsConstructor
public class OffersController {
    private final UserService userService;
    private final OffersService offersService;
    private final ObjectsService objectsService;
    private final ServicesService servicesService;
    private final RequestService requestService;

    @GetMapping("/offers")
    public String offers(@RequestParam(name = "show_my", required = false) Boolean showMy, Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        List<Offer> offers;
        if (showMy != null && showMy) {
            offers = offersService.getUserOffers(userId);
        } else {
            offers = offersService.getOffers(userId);
        }
        model.addAttribute("offers", offers);
        model.addAttribute("newOffer", new Offer());
        model.addAttribute("showMy", showMy);
        model.addAttribute("userName", authentication.getName());
        return "offers";
    }

    @PostMapping("/offers/delete/{id}")
    public String delete(Authentication authentication, Model model, @PathVariable Integer id) {
        offersService.deleteOffer(id);
        return offers(false, authentication, model);
    }

    @GetMapping("/offers/create")
    public String createOfferPage(Model model) {
        model.addAttribute("newOffer", new Offer());
        return "create_offer";
    }

    @PostMapping("/offers/create")
    public String createOffers(@ModelAttribute Offer offer, Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        offersService.createOffer(offer, userId);
        return offers(false, authentication, model);
    }

    @GetMapping("/offers/request/{id}")
    public String request(Authentication authentication, Model model, @PathVariable Integer id) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        model.addAttribute("offer", offersService.getOffer(id));
        model.addAttribute("objects", objectsService.getUserObject(userId).stream().filter(element -> element.getState() == ObjectState.IN_STOCK).collect(Collectors.toList()));
        model.addAttribute("services", servicesService.getUserService(userId));
        model.addAttribute("offerRequest", new OfferRequestDTO());
        return "create_request_offer";
    }

    @PostMapping("/offers/request/{id}")
    public String createRequest(Authentication authentication, @ModelAttribute OfferRequestDTO offerRequest,
                                @PathVariable Integer id, Model model) {
        Offer offer = offersService.getOffer(id);
        model.addAttribute("item", offer);
        Integer userId = userService.getUser(authentication.getName()).getId();
        offerRequest.setUserId(userId);
        offerRequest.setOfferId(offer.getId());
        requestService.createOfferRequest(offerRequest);
        return "request_recorded";
    }

    @GetMapping("/offers/{id}")
    public String openOfferPage(Authentication authentication, @PathVariable Integer id, Model model) {
        Offer offer = offersService.getOffer(id);
        model.addAttribute("offer", offer);
        model.addAttribute("userName", authentication.getName());
        List<Request> requests = new ArrayList<>();
        requests.addAll(offer.getObjectOfferRequests());
        requests.addAll(offer.getServiceOfferRequests());
        if (offer.getUser().getUsername().equals(authentication.getName())) {
            model.addAttribute("requests", requests);
        } else {
            model.addAttribute("requests", requests.stream().filter(element -> element.getUser().getUsername().equals(authentication.getName())).collect(Collectors.toList()));
        }
        return "offer_page";
    }

    @PostMapping("/offers/request/delete/{id}")
    public String deleteRequest(Authentication authentication, Model model, @PathVariable Integer id) {
        requestService.deleteRequest(id);
        return offers(false, authentication, model);
    }

    @PostMapping("/offers/request/approve/{id}")
    public String approveRequest(Authentication authentication, Model model, @PathVariable Integer id) {
        requestService.approveRequest(id);
        return offers(false, authentication, model);
    }
}
