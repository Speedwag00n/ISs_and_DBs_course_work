package sharing.dormitory.service;

import sharing.dormitory.dto.OfferRequestDTO;
import sharing.dormitory.dto.SuggestionRequestDTO;

public interface RequestService {
    void createOfferRequest(OfferRequestDTO offerRequest);
    void createSuggestionRequest(SuggestionRequestDTO suggestionRequest);
    void deleteRequest(Integer id);
    void approveRequest(Integer id);
}
