package sharing.dormitory.service;

import sharing.dormitory.db.model.Suggestion;
import sharing.dormitory.dto.SuggestionDTO;

import java.util.List;

public interface SuggestionsService {

    List<Suggestion> getSuggestions(Integer userId);

    List<Suggestion> getUserSuggestions(Integer userId);

    void createSuggestion(SuggestionDTO suggestion, Integer id);

    void deleteSuggestion(Integer id);

    Suggestion getSuggestion(Integer id);
}
