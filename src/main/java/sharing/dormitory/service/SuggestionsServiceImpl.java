package sharing.dormitory.service;

import lombok.AllArgsConstructor;
import sharing.dormitory.db.model.Dormitory;
import sharing.dormitory.db.model.Suggestion;
import sharing.dormitory.db.repository.SuggestionRepository;
import sharing.dormitory.db.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class SuggestionsServiceImpl implements SuggestionsService {
    @PersistenceContext
    private final EntityManager entityManager;
    private final SuggestionRepository suggestionRepository;
    private final UserRepository userRepository;

    @Override
    public List<Suggestion> getSuggestions(Integer userId) {
        Dormitory dormitory = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new).getDormitory();
        StoredProcedureQuery serviceQuery = entityManager.createNamedStoredProcedureQuery("getServiceSuggestionInDormitory");
        serviceQuery.setParameter("dormitoryId", dormitory.getId());
        serviceQuery.execute();
        List<Suggestion> result = serviceQuery.getResultList();
        StoredProcedureQuery objectQuery = entityManager.createNamedStoredProcedureQuery("getObjectSuggestionInDormitory");
        objectQuery.setParameter("dormitoryId", dormitory.getId());
        objectQuery.execute();
        result.addAll((List<Suggestion>) objectQuery.getResultList());
        return  result;
    }

    @Override
    public List<Suggestion> getUserSuggestions(Integer userId) {
        return suggestionRepository.findAllByUserId(userId);
    }

    @Override
    public void createSuggestion(Suggestion suggestion, Integer userId) {
        suggestion.setUser(userRepository.findById(userId).orElseThrow(IllegalArgumentException::new));
        suggestionRepository.save(suggestion);
    }

    @Override
    public void deleteSuggestion(Integer id) {
        suggestionRepository.deleteById(id);
    }
}
