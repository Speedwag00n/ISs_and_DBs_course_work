package sharing.dormitory.service;

import sharing.dormitory.db.enm.Status;
import sharing.dormitory.db.model.Offer;

import java.util.List;

public interface OffersService {

    List<Offer> getOffers(Integer userId);

    void createOffer(Offer offer, Integer id);

}
