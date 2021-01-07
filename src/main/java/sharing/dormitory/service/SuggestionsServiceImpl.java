package sharing.dormitory.service;

import lombok.AllArgsConstructor;
import sharing.dormitory.db.enm.Status;
import sharing.dormitory.db.model.Dormitory;
import sharing.dormitory.db.model.ObjectSuggestion;
import sharing.dormitory.db.model.ServiceSuggestion;
import sharing.dormitory.db.model.Suggestion;
import sharing.dormitory.db.repository.SuggestionRepository;
import sharing.dormitory.db.repository.UserRepository;
import sharing.dormitory.dto.SuggestionDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class SuggestionsServiceImpl implements SuggestionsService {
    @PersistenceContext
    private final EntityManager entityManager;
    private final SuggestionRepository suggestionRepository;
    private final UserRepository userRepository;

    @Override
    public List getSuggestions(Integer userId) {
        List<Suggestion> result = new ArrayList<>();
        Dormitory dormitory = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new).getDormitory();
        StoredProcedureQuery serviceQuery = entityManager.createNamedStoredProcedureQuery("getServiceSuggestionInDormitory");
        serviceQuery.setParameter("dormitoryId", dormitory.getId());
        serviceQuery.execute();
        result.addAll((List<Suggestion>)serviceQuery.getResultList());
        StoredProcedureQuery objectQuery = entityManager.createNamedStoredProcedureQuery("getObjectSuggestionInDormitory");
        objectQuery.setParameter("dormitoryId", dormitory.getId());
        objectQuery.execute();
        result.addAll((List<Suggestion>)objectQuery.getResultList());
        return result;
    }

    @Override
    public List<Suggestion> getUserSuggestions(Integer userId) {
        List<Suggestion> result = suggestionRepository.findAllByUserId(userId);
        return result;
    }

    @Override
    public void createSuggestion(SuggestionDTO suggestionDTO, Integer userId) {
        Suggestion suggestion = new Suggestion();
        suggestion.setName(suggestionDTO.getName());
        suggestion.setDescription(suggestionDTO.getDescription());
        suggestion.setOfferStatus(Status.OPEN);
        suggestion.setCreationDate(OffsetDateTime.now());
        suggestion.setUser(userRepository.findById(userId).orElseThrow(IllegalArgumentException::new));
        if (suggestionDTO.getObject() != null) {

        } else if (suggestionDTO.getService() != null) {

        }
        suggestionRepository.save(suggestion);
    }

    @Override
    public void deleteSuggestion(Integer id) {
        suggestionRepository.deleteById(id);
    }

    @Override
    public Suggestion getSuggestion(Integer id) {
        return suggestionRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
