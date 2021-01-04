package sharing.dormitory.service;

import sharing.dormitory.db.model.Suggestion;

import java.util.List;

public interface SuggestionsService {

    List<Suggestion> getSuggestions(Integer userId);

    List<Suggestion> getUserSuggestions(Integer userId);

    void createSuggestion(Suggestion offer, Integer id);

    void deleteSuggestion(Integer id);
}
