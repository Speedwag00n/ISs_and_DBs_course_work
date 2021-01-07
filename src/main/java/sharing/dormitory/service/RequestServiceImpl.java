package sharing.dormitory.service;

import lombok.AllArgsConstructor;
import sharing.dormitory.db.model.Offer;
import sharing.dormitory.db.model.Suggestion;
import sharing.dormitory.dto.OfferRequestDTO;
import sharing.dormitory.dto.SuggestionRequestDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.Objects;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {
    @PersistenceContext
    private final EntityManager entityManager;
    private final OffersService offersService;
    private final SuggestionsService suggestionsService;
    public void createOfferRequest(OfferRequestDTO offerRequest) {

        // Get Offer from DTO
        Offer offer = offersService.getOffer(offerRequest.getOfferId());
        StoredProcedureQuery query;
        if (Objects.nonNull(offerRequest.getObjectId())) {
            query = entityManager.createNamedStoredProcedureQuery("insertObjectOfferRequest");
            query.setParameter("object", offerRequest.getObjectId());
        }
        else if (Objects.nonNull(offerRequest.getServiceId())) {
            query = entityManager.createNamedStoredProcedureQuery("insertServiceOfferRequest");
            query.setParameter("service", offerRequest.getServiceId());

        } else throw new IllegalArgumentException("Wrong value of OfferRequestDTO");
        query.setParameter("name", offer.getName());
        query.setParameter("content", offerRequest.getDescription());
        query.setParameter("author", offerRequest.getUserId());
        query.setParameter("offer", offerRequest.getOfferId());
        query.execute();
    }

    public void createSuggestionRequest(SuggestionRequestDTO suggestionRequest) {
        Suggestion suggestion = suggestionsService.getSuggestion(suggestionRequest.getSuggestionId());
        StoredProcedureQuery   query = entityManager.createNamedStoredProcedureQuery("insertSuggestionRequest");
        query.setParameter("name", suggestion.getName());
        query.setParameter("content", suggestionRequest.getDescription());
        query.setParameter("author", suggestionRequest.getUserId());
        query.setParameter("suggestion", suggestionRequest.getSuggestionId());
        query.execute();
    }
}
