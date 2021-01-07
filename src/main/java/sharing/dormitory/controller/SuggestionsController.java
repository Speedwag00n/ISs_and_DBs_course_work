package sharing.dormitory.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sharing.dormitory.db.model.ObjectSuggestion;
import sharing.dormitory.db.model.ServiceSuggestion;
import sharing.dormitory.db.model.Suggestion;
import sharing.dormitory.dto.SuggestionDTO;
import sharing.dormitory.service.ObjectsService;
import sharing.dormitory.service.ServicesService;
import sharing.dormitory.service.SuggestionsService;
import sharing.dormitory.service.UserService;

import java.util.List;


@Controller
@AllArgsConstructor
public class SuggestionsController {
    private final UserService userService;
    private final SuggestionsService suggestionsService;
    private final ObjectsService objectsService;
    private final ServicesService servicesService;

    @GetMapping("/suggestions")
    public String suggestions(@RequestParam(name = "show_my", required = false) Boolean showMy, Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        List<Suggestion> suggestions;
        if (showMy != null && showMy) {
            suggestions = suggestionsService.getUserSuggestions(userId);
        } else {
            suggestions = suggestionsService.getSuggestions(userId);
        }
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("newSuggestion", new Suggestion());
        model.addAttribute("showMy", showMy);
        model.addAttribute("userName", authentication.getName());
        return "suggestions";
    }

    @GetMapping("/suggestions/delete/{id}")
    public String delete(Authentication authentication, Model model, @PathVariable Integer id) {
        suggestionsService.deleteSuggestion(id);
        return suggestions(false, authentication, model);
    }

    @GetMapping("/suggestions/create")
    public String createSuggestionPage(Authentication authentication,Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        model.addAttribute("newSuggestion", new SuggestionDTO());
        model.addAttribute("objects", objectsService.getUserObject(userId));
        model.addAttribute("services", servicesService.getUserService(userId));
        return "create_suggestion";
    }

    @PostMapping("/suggestions/create")
    public String createSuggestions(@ModelAttribute SuggestionDTO suggestionDTO, Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        suggestionsService.createSuggestion(suggestionDTO, userId);
        return suggestions(false, authentication, model);
    }

    @GetMapping("/suggestions/{id}")
    public String openSuggestionPage(Authentication authentication, @PathVariable Integer id, Model model) {
        Suggestion suggestion = suggestionsService.getSuggestion(id);
        model.addAttribute("suggestion", suggestion);
        if (suggestion instanceof ObjectSuggestion) {
            model.addAttribute("type", "object");
        } else if (suggestion instanceof ServiceSuggestion) {
            model.addAttribute("type", "service");
        }
        model.addAttribute("userName", authentication.getName());
        return "suggestion_page";
    }
}
