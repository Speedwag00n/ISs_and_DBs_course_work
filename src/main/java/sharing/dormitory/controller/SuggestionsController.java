package sharing.dormitory.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import sharing.dormitory.db.model.ObjectSuggestion;
import sharing.dormitory.db.model.ServiceSuggestion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sharing.dormitory.db.model.Suggestion;
import sharing.dormitory.dto.SuggestionDTO;
import sharing.dormitory.dto.SuggestionRequestDTO;
import sharing.dormitory.service.ObjectsService;
import sharing.dormitory.service.RequestService;
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
    private final RequestService requestService;

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

    @PostMapping("/suggestions/delete/{id}")
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

    @GetMapping("/suggestions/request/{id}")
    public String request(Authentication authentication, Model model, @PathVariable Integer id) {
        Integer userId = userService.getUser(authentication.getName()).getId();
        model.addAttribute("suggestion", suggestionsService.getSuggestion(id));
        model.addAttribute("suggestionRequest", new SuggestionRequestDTO());
        return "create_request_suggestion";
    }

    @PostMapping("/suggestions/request/{id}")
    public String createRequest(Authentication authentication, @ModelAttribute SuggestionRequestDTO suggestionRequest,
                                @PathVariable Integer id, Model model) {
        Suggestion suggestion = suggestionsService.getSuggestion(id);
        model.addAttribute("item", suggestion);
        Integer userId = userService.getUser(authentication.getName()).getId();
        suggestionRequest.setUserId(userId);
        suggestionRequest.setSuggestionId(suggestion.getId());
        requestService.createSuggestionRequest(suggestionRequest);
        return "request_recorded";
//        вот тут страничка что реквест успешно отправлен как для офферов
    }
}
