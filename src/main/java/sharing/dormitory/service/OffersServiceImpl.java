package sharing.dormitory.service;

import lombok.AllArgsConstructor;
import sharing.dormitory.db.enm.Status;
import sharing.dormitory.db.model.Offer;
import sharing.dormitory.db.repository.OfferRepository;
import sharing.dormitory.db.repository.UserRepository;

import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class OffersServiceImpl implements OffersService {
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    @Override
    public List<Offer> getOffers() {
        return offerRepository.findAllInDormitory(1, Status.OPEN);
    }

    @Override
    public void createOffer(Offer offer, Integer id) {
        offer.setUser(userRepository.findById(id).orElseThrow(IllegalArgumentException::new));
        offerRepository.save(offer);
    }
}
