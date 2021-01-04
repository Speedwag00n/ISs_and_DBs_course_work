package sharing.dormitory.service;

import sharing.dormitory.db.model.Offer;

import java.util.List;

public interface OffersService {

    List<Offer> getOffers();

    void createOffer(Offer offer, Integer id);

}
