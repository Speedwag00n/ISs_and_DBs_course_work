package sharing.dormitory.service;

import sharing.dormitory.db.model.Offer;

import java.util.List;

public interface OffersService {

    List<Offer> getOffers(Integer userId);

    List<Offer> getUserOffers(Integer userId);

    void createOffer(Offer offer, Integer id);

    void deleteOffer(Integer id);

    Offer getOffer(Integer id);
}
