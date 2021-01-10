package sharing.dormitory.service;

import lombok.AllArgsConstructor;
import sharing.dormitory.db.enm.ObjectState;
import sharing.dormitory.db.enm.Status;
import sharing.dormitory.db.model.*;
import sharing.dormitory.db.model.Object;
import sharing.dormitory.db.repository.ObjectRepository;
import sharing.dormitory.db.repository.RequestRepository;
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
    private final RequestRepository requestRepository;
    private final ObjectRepository objectRepository;
    public void createOfferRequest(OfferRequestDTO offerRequest) {

        // Get Offer from DTO
        Offer offer = offersService.getOffer(offerRequest.getOfferId());
        StoredProcedureQuery query;
        if (Objects.nonNull(offerRequest.getObjectId())) {
            query = entityManager.createNamedStoredProcedureQuery("insertObjectOfferRequest");
            query.setParameter("object_id", offerRequest.getObjectId());
            System.out.println("alarm");
            System.out.println(offerRequest.getObjectId());
        }
        else if (Objects.nonNull(offerRequest.getServiceId())) {
            query = entityManager.createNamedStoredProcedureQuery("insertServiceOfferRequest");
            query.setParameter("service", offerRequest.getServiceId());

        } else throw new IllegalArgumentException("Wrong value of OfferRequestDTO");
        query.setParameter("name", offerRequest.getName());
        query.setParameter("content", offerRequest.getDescription());
        query.setParameter("author", offerRequest.getUserId());
        query.setParameter("offer", offerRequest.getOfferId());
        query.execute();
    }

    public void createSuggestionRequest(SuggestionRequestDTO suggestionRequest) {
        Suggestion suggestion = suggestionsService.getSuggestion(suggestionRequest.getSuggestionId());
        StoredProcedureQuery   query = entityManager.createNamedStoredProcedureQuery("insertSuggestionRequest");
        query.setParameter("name", suggestionRequest.getName());
        query.setParameter("content", suggestionRequest.getDescription());
        query.setParameter("author", suggestionRequest.getUserId());
        query.setParameter("suggestion", suggestionRequest.getSuggestionId());
        query.execute();
    }

    @Override
    public void deleteRequest(Integer id) {
        Request request = requestRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if (request instanceof ObjectOfferRequest) {
            Object object = ((ObjectOfferRequest) request).getObject();
            object.setState(ObjectState.IN_STOCK);
            objectRepository.save(object);
        }
        requestRepository.deleteById(id);
    }

    @Override
    public void approveRequest(Integer id) {
        Request request = requestRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if (request instanceof ObjectOfferRequest) {
            ((ObjectOfferRequest) request).getOffer().setOfferStatus(Status.RESOLVED);
        } else if (request instanceof ServiceOfferRequest) {
            ((ServiceOfferRequest) request).getOffer().setOfferStatus(Status.RESOLVED);
        } else if (request instanceof SuggestionRequest) {
            ((SuggestionRequest) request).getSuggestion().setOfferStatus(Status.RESOLVED);
        }
        requestRepository.save(request);
    }
}
