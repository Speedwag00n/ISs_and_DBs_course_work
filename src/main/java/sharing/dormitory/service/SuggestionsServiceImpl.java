package sharing.dormitory.service;

import lombok.AllArgsConstructor;
import sharing.dormitory.db.enm.ObjectState;
import sharing.dormitory.db.model.Dormitory;
import sharing.dormitory.db.model.Object;
import sharing.dormitory.db.model.ObjectSuggestion;
import sharing.dormitory.db.model.Suggestion;
import sharing.dormitory.db.repository.ObjectRepository;
import sharing.dormitory.db.repository.ServiceRepository;
import sharing.dormitory.db.repository.SuggestionRepository;
import sharing.dormitory.db.repository.UserRepository;
import sharing.dormitory.dto.SuggestionDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class SuggestionsServiceImpl implements SuggestionsService {
    @PersistenceContext
    private final EntityManager entityManager;
    private final SuggestionRepository suggestionRepository;
    private final ObjectRepository objectRepository;
    private final ServiceRepository serviceRepository;
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
        StoredProcedureQuery query;
        if (Objects.nonNull(suggestionDTO.getObject())) {
            query = entityManager.createNamedStoredProcedureQuery("insertObjectSuggestion");
            query.setParameter("OBJECT_ID", suggestionDTO.getObject());
        } else if (Objects.nonNull(suggestionDTO.getService())) {
            query = entityManager.createNamedStoredProcedureQuery("insertServiceSuggestion");
            query.setParameter("SERVICE_ID", suggestionDTO.getService());
        } else {
            throw new IllegalArgumentException("Wrong value of SuggestionDTO");
        }
        query.setParameter("NAME_SUGGESTION", suggestionDTO.getName());
        query.setParameter("DESCRIPTION_SUGGESTION", suggestionDTO.getDescription());
        query.setParameter("AUTHOR_ID", userId);
        query.execute();
    }

    @Override
    public void deleteSuggestion(Integer id) {
        Suggestion suggestion = suggestionRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if (suggestion instanceof ObjectSuggestion) {
            Object object = ((ObjectSuggestion) suggestion).getObject();
            object.setState(ObjectState.IN_STOCK);
            objectRepository.save(object);
        }
        suggestionRepository.deleteById(id);
    }

    @Override
    public Suggestion getSuggestion(Integer id) {
        return suggestionRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
