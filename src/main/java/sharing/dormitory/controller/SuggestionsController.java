package sharing.dormitory.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sharing.dormitory.db.model.Suggestion;
import sharing.dormitory.service.SuggestionsService;
import sharing.dormitory.service.UserService;

import java.util.List;


@Controller
@AllArgsConstructor
public class SuggestionsController {
    private final UserService userService;
    private final SuggestionsService suggestionsService;

    @GetMapping("/allsuggestions")
    public String allSuggestions(Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        List<Suggestion> suggestions = suggestionsService.getSuggestions(userId);
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("newSuggestion", new Suggestion());
        return "allsuggestions";
    }

    @GetMapping("/suggestions")
    public String suggestions(Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        List<Suggestion> suggestions = suggestionsService.getUserSuggestions(userId);
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("newSuggestion", new Suggestion());
        return "suggestions";
    }

    @GetMapping("/suggestions/delete/{id}")
    public String delete(Authentication authentication, Model model, @PathVariable Integer id) {
        suggestionsService.deleteSuggestion(id);
        return suggestions(authentication, model);
    }

    @PostMapping("/suggestions/create")
    public String createSuggestions(@ModelAttribute Suggestion offer, Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        suggestionsService.createSuggestion(offer, userId);
        return suggestions(authentication, model);
    }
}
