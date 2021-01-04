package sharing.dormitory.service;

import lombok.AllArgsConstructor;
import sharing.dormitory.db.enm.Status;
import sharing.dormitory.db.model.Dormitory;
import sharing.dormitory.db.model.Offer;
import sharing.dormitory.db.repository.OfferRepository;
import sharing.dormitory.db.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class OffersServiceImpl implements OffersService {
    @PersistenceContext
    private EntityManager entityManager;

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    @Override
    public List<Offer> getOffers(Integer userId) {
        Dormitory dormitory = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new).getDormitory();

        Query query = entityManager.createNamedStoredProcedureQuery("getOffersInDormitory");
        query.setParameter("dormitoryId", dormitory.getId());
        ((StoredProcedureQuery) query).execute();
        List<Offer> offers = query.getResultList();

        return offers;
    }

    @Override
    public void createOffer(Offer offer, Integer id) {
        offer.setUser(userRepository.findById(id).orElseThrow(IllegalArgumentException::new));
        offerRepository.save(offer);
    }
}
